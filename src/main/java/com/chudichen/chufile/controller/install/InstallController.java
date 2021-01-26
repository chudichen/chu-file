package com.chudichen.chufile.controller.install;

import cn.hutool.crypto.SecureUtil;
import com.chudichen.chufile.model.dto.SystemConfigDTO;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.SystemConfigService;
import com.chudichen.chufile.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统安装初始化
 *
 * @author chudichen
 * @date 2021-01-26
 */
@RestController
public class InstallController {

    private final SystemConfigService systemConfigService;

    public InstallController(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    @GetMapping("/is-installed")
    public ResultBean isInstall() {
        if (StringUtils.isNotNullOrEmpty(systemConfigService.getAdminUsername())) {
            return ResultBean.error("请勿重复初始化");
        }
        return ResultBean.success();
    }

    @PostMapping("/install")
    public ResultBean install(SystemConfigDTO systemConfigDTO) {
        if (!StringUtils.isNullOrEmpty(systemConfigService.getAdminUsername())) {
            return ResultBean.error("请勿重复初始化");
        }

        systemConfigDTO.setPassword(SecureUtil.md5(systemConfigDTO.getPassword()));
        systemConfigService.updateSystemConfig(systemConfigDTO);
        return ResultBean.success();
    }
}
