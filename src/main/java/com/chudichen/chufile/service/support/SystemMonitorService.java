package com.chudichen.chufile.service.support;

import com.chudichen.chufile.model.support.SystemMonitorInfo;
import org.springframework.stereotype.Service;

/**
 * @author chudichen
 * @date 2021-01-26
 */
@Service
public class SystemMonitorService {

    public SystemMonitorInfo systemMonitorInfo() {
        return new SystemMonitorInfo();
    }
}
