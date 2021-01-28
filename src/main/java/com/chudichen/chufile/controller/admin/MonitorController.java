package com.chudichen.chufile.controller.admin;

import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.model.support.SystemMonitorInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chudichen
 * @date 2021-01-28
 */
@RestController
@RequestMapping("/admin")
public class MonitorController {

    /**
     * 获取系统监控信息
     */
    @GetMapping("monitor")
    public ResultBean monitor() {
        return ResultBean.success(new SystemMonitorInfo());
    }
}
