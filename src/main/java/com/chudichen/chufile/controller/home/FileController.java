package com.chudichen.chufile.controller.home;

import com.alibaba.fastjson.JSON;
import com.chudichen.chufile.context.DriveContext;
import com.chudichen.chufile.exception.NotExistFileException;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import com.chudichen.chufile.model.dto.FileItemDTO;
import com.chudichen.chufile.model.dto.SystemFrontConfigDTO;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.model.support.FilePageModel;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.DriveConfigService;
import com.chudichen.chufile.service.FilterConfigService;
import com.chudichen.chufile.service.SystemConfigService;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import com.chudichen.chufile.util.FileComparator;
import com.chudichen.chufile.util.HttpUtil;
import com.chudichen.chufile.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

/**
 * 前台文件管理系统
 *
 * @author chudichen
 * @date 2021-01-21
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class FileController {

    /**
     *  公洞加载每页条数
     */
    private static final Integer PAGE_SIZE = 30;

    private final SystemConfigService systemConfigService;
    private final DriveContext driveContext;
    private final DriveConfigService driveConfigService;
    private final FilterConfigService filterConfigService;

    public FileController(SystemConfigService systemConfigService,
                          DriveContext driveContext,
                          DriveConfigService driveConfigService,
                          FilterConfigService filterConfigService) {
        this.systemConfigService = systemConfigService;
        this.driveContext = driveContext;
        this.driveConfigService = driveConfigService;
        this.filterConfigService = filterConfigService;
    }

    /**
     * 获取所有已启用的驱动器
     *
     * @return 素有已启用驱动器
     */
    @GetMapping("/drive/list")
    public ResultBean drives() {
        return ResultBean.success(driveConfigService.listOnlyEnable());
    }

    /**
     * 获取某个驱动器下，指定路径的数据，每页固定{@link #PAGE_SIZE}条数据
     *
     * @param driveId 驱动器id
     * @param path 路径
     * @param password 文件夹密码，某些文件需要密码才能访问，而有些不需要
     * @param page 页码
     * @return 当前路径下的文件和文件夹
     */
    @GetMapping("/list/{driveId}")
    public ResultBean list(@PathVariable(name = "driveId") Integer driveId,
                           @RequestParam(defaultValue = "/") String path,
                           @RequestParam(required = false) String password,
                           @RequestParam(defaultValue = "1") Integer page) throws Exception {
        AbstractBaseFileService fileService = driveContext.get(driveId);
        List<FileItemDTO> fileItemDTOList = fileService.fileList(ChuFileConstant.PATH_SEPARATOR + path + ChuFileConstant.PATH_SEPARATOR);
        for (FileItemDTO fileItemDTO : fileItemDTOList) {
            if (ChuFileConstant.PASSWORD_FILE_NAME.equals(fileItemDTO.getName())) {
                String expectedPasswordContent;
                try {
                    expectedPasswordContent = HttpUtil.getTextContent(fileItemDTO.getUrl());
                } catch (HttpClientErrorException ex) {
                    log.error("尝试重新获取密码文件缓存中链接后仍失败, driveId: {}, path: {}, inputPassword: {}, passwordFile:{} ",
                            driveId, path, password, JSON.toJSONString(fileItemDTO), ex);
                    try {
                        String fullPath = StringUtils.removeDuplicateSeparator(fileItemDTO.getPath() + ChuFileConstant.PATH_SEPARATOR + fileItemDTO.getName());
                        FileItemDTO fileItem = fileService.getFileItem(fullPath);
                        expectedPasswordContent = HttpUtil.getTextContent(fileItem.getUrl());
                    } catch (Exception e) {
                        log.error("尝试重新获取密码文件链接后仍失败, 已暂时取消密码", e);
                        break;
                    }
                }

                if (Objects.equals(expectedPasswordContent, password)) {
                    break;
                }

                if (StringUtils.isNotNullOrEmpty(password)) {
                    return ResultBean.error("密码错误.", ResultBean.INVALID_PASSWORD);
                }
                return ResultBean.error("此文件夹需要密码.", ResultBean.REQUIRED_PASSWORD);
            }
        }

        // 过滤掉表达式中不存在的数据
        fileItemDTOList.removeIf(next -> filterConfigService.filterResultIsHidden(driveId, StringUtils.concatUrl(next.getPath(), next.getName())));
        return ResultBean.successData(getSortedPagingData(fileItemDTOList, page));
    }

    /**
     * 获取系统配置信息和当前页的标题，页面党文信息
     *
     * @param driveId 驱动器id
     * @param path 路径
     * @return 返回指定驱动器的系统配置信息
     */
    @GetMapping("/config/{driveId}")
    public ResultBean getConfig(@PathVariable(name = "driveId") Integer driveId, String path) {
        AbstractBaseFileService fileService = driveContext.get(driveId);
        SystemFrontConfigDTO systemConfig = systemConfigService.getSystemFrontConfig(driveId);
        DriveConfig driveConfig = driveConfigService.findById(driveId);
        String fullPath = StringUtils.removeDuplicateSeparator(path + ChuFileConstant.PATH_SEPARATOR + ChuFileConstant.README_FILE_NAME);
        FileItemDTO fileItem = null;
        try {
            fileItem = fileService.getFileItem(fullPath);
            if (!Objects.equals(driveConfig.getType(), StorageStrategyEnum.FTP)) {
                String readme = HttpUtil.getTextContent(fileItem.getUrl());
                systemConfig.setReadme(readme);
            }
        } catch (NotExistFileException e) {
            log.debug("不存在README文件,已跳过,fullPath:{}，fileItem:{}", fullPath, JSON.toJSONString(fileItem));
        } catch (Exception e) {
            log.error("获取 README 文件异常, fullPath: {}, fileItem: {}", fullPath, JSON.toJSONString(fileItem), e);
        }
        return ResultBean.successData(systemConfig);
    }

    @GetMapping("/search/{driveId}")
    public ResultBean search(@RequestParam(value = "name", defaultValue = "/") String name,
                             @RequestParam(defaultValue = "name") String sortBy,
                             @RequestParam(defaultValue = "asc") String order,
                             @RequestParam(defaultValue = "1") Integer page,
                             @PathVariable("driveId") Integer driveId) {
        return ResultBean.error("暂不支持搜索功能");
    }
    /**
     * 对传入文件列表，按照文件名进行排序，然后取相应页数的文件
     *
     * @param fileItemDTOList 文件列表
     * @param page 要取的页数
     * @return 排序及分页后的那段数据
     */
    private FilePageModel getSortedPagingData(List<FileItemDTO> fileItemDTOList, Integer page) {
        List<FileItemDTO> copy = new ArrayList<>(Arrays.asList(new FileItemDTO[fileItemDTOList.size()]));
        Collections.copy(copy, fileItemDTOList);

        // 排序，先按照文件类型比较，文件夹在前，文件在后，然后根据sortBy字段排序，默认为升序
        copy.sort(new FileComparator());
        filterFileList(copy);

        int total = copy.size();
        int totalPage = (total + PAGE_SIZE - 1) / PAGE_SIZE;

        if (page > totalPage) {
            return new FilePageModel(totalPage, Collections.emptyList());
        }

        int start = (page - 1) * PAGE_SIZE;
        int end = page * PAGE_SIZE;
        end = Math.min(end, total);
        return new FilePageModel(totalPage, copy.subList(start, end));
    }

    /**
     * 过滤文件列表，去除密码，文档文件
     *
     * @param fileItemDTOList 文件列表
     */
    private void filterFileList(List<FileItemDTO> fileItemDTOList) {
        if (CollectionUtils.isEmpty(fileItemDTOList)) {
            return;
        }

        fileItemDTOList.removeIf(fileItemDTO -> ChuFileConstant.PASSWORD_FILE_NAME.equals(fileItemDTO.getName()) ||
                ChuFileConstant.README_FILE_NAME.equals(fileItemDTO.getName()));
    }
}
