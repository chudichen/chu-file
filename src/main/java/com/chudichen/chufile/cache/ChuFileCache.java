package com.chudichen.chufile.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件缓存类
 *
 * @author chudichen
 * @date 2021-01-20
 */
@Slf4j
@Component
public class ChuFileCache {

    @Autowired
    private DriverConfigRespository driverConfigRepository;

    /**
     * 缓存过期时间
     */
    @Value("${chu.file.cache.timeout}")
    private Long timeout;

    /**
     * 缓存自动刷新间隔
     */
    @Value("${chu.file.cache.auto-refresh.interval}")
    private Long autoRefreshInterval;

//    private ConcurrentMap<Integer, >

}
