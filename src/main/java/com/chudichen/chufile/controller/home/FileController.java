package com.chudichen.chufile.controller.home;

import com.chudichen.chufile.context.DriveContext;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.DriveConfigService;
import com.chudichen.chufile.service.FilterConfigService;
import com.chudichen.chufile.service.SystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list/{driveId}")
    public ResultBean list(@PathVariable(name = "driveId") Integer driveId,
                           @RequestParam(defaultValue = "/") String path,
                           @RequestParam(required = false) String password,
                           @RequestParam(defaultValue = "1") Integer page) {
        return ResultBean.success();
    }
}
