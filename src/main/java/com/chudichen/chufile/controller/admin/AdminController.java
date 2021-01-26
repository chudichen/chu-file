package com.chudichen.chufile.controller.admin;

import com.chudichen.chufile.model.dto.SystemConfigDTO;
import com.chudichen.chufile.model.entity.SystemConfig;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.SystemConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理后台接口
 *
 * @author chudichen
 * @date 2021-01-26
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final SystemConfigService systemConfigService;

    public AdminController(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    /**
     * 获取系统配置
     *
     * @return 系统配置
     */
    @GetMapping("/config")
    public ResultBean getConfig() {
        SystemConfigDTO systemConfigDTO = systemConfigService.getSystemConfig();
        return ResultBean.success(systemConfigDTO);
    }

    /**
     * 更新系统配置
     *
     * @param systemConfigDTO 系统配置
     * @return 结果
     */
    public ResultBean updateConfig(SystemConfigDTO systemConfigDTO) {
        systemConfigDTO.setId(1);
        systemConfigService.updateSystemConfig(systemConfigDTO);
        return ResultBean.success();
    }
}
