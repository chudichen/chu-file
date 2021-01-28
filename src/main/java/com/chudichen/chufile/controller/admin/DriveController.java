package com.chudichen.chufile.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.chudichen.chufile.model.dto.DriveConfigDTO;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.entity.FilterConfig;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.DriveConfigService;
import com.chudichen.chufile.service.FilterConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 驱动器相关操作Controller
 *
 * @author chudichen
 * @date 2021-01-28
 */
@RestController
@RequestMapping("/admin")
public class DriveController {

    private final DriveConfigService driveConfigService;
    private final FilterConfigService filterConfigService;

    public DriveController(DriveConfigService driveConfigService, FilterConfigService filterConfigService) {
        this.driveConfigService = driveConfigService;
        this.filterConfigService = filterConfigService;
    }

    /**
     * 获取所有驱动器列表
     *
     * @return 驱动器列表
     */
    @GetMapping("/drives")
    public ResultBean driveList() {
        List<DriveConfig> list = driveConfigService.list();
        return ResultBean.success(list);
    }

    /**
     * 获取指定驱动器基本信息及其参数
     *
     * @param id 驱动器id
     * @return 驱动器基本信息
     */
    @GetMapping("/drive/{id}")
    public ResultBean driveItem(@PathVariable Integer id) {
        DriveConfigDTO driveConfigDTO = driveConfigService.findDriveConfigDTOById(id);
        return ResultBean.success(driveConfigDTO);
    }

    /**
     * 保存驱动器设置
     *
     * @param driveConfigDTO 配置
     * @return 是否成功
     */
    @PostMapping("/drive")
    public ResultBean saveDriveItem(@RequestBody DriveConfigDTO driveConfigDTO) {
        driveConfigService.saveDriveConfigDTO(driveConfigDTO);
        return ResultBean.success();
    }

    /**
     * 启用驱动器
     *
     * @param id 驱动器id
     * @return 是否成功
     */
    @PostMapping("/drive/{id}/enable")
    public ResultBean enable(@PathVariable("id") Integer id) {
        DriveConfig driveConfig = driveConfigService.findById(id);
        driveConfig.setEnable(true);
        driveConfigService.saveOrUpdate(driveConfig);
        return ResultBean.success();
    }

    /**
     * 停止驱动器
     *
     * @param id 驱动器 ID
     * @return 是否成功
     */
    @PostMapping("/drive/{id}/disable")
    public ResultBean disable(@PathVariable("id") Integer id) {
        DriveConfig driveConfig = driveConfigService.findById(id);
        driveConfig.setEnable(false);
        driveConfigService.saveOrUpdate(driveConfig);
        return ResultBean.success();
    }

    @GetMapping("/drive/{id}/filters")
    public ResultBean getFilters(@PathVariable("id") Integer id) {
        return ResultBean.success(filterConfigService.findByDriveId(id));
    }

    @PostMapping("/drive/{id}/filters")
    public ResultBean saveFilters(@RequestBody List<FilterConfig> filter, @PathVariable("id") Integer driveId) {
        filterConfigService.batchSave(filter, driveId);
        return ResultBean.success();
    }

    @PostMapping("/drive/drag")
    public ResultBean saveDriveDrag(@RequestBody List<JSONObject> driveConfigs) {
        driveConfigService.saveDriveDrag(driveConfigs);
        return ResultBean.success();
    }
}
