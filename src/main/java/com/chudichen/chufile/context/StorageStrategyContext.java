package com.chudichen.chufile.context;

import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 存储策略工厂
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Component
public class StorageStrategyContext implements ApplicationContextAware {

    private static Map<String, AbstractBaseFileService> storageStrategyEnumFileServiceMap;
    private static ApplicationContext applicationContext;

    /**
     * 项目启动时执行
     *
     * @param applicationContext 上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        StorageStrategyContext.applicationContext = applicationContext;
        // 获取 Spring 容器中所有 FileService 类型的类
        StorageStrategyContext.storageStrategyEnumFileServiceMap = applicationContext.getBeansOfType(AbstractBaseFileService.class);
    }

    /**
     * 获取指定存储类型Service
     *
     * @param strategy 策略
     * @return service
     */
    public static AbstractBaseFileService getStorageStrategyService(StorageStrategyEnum strategy) {
        return storageStrategyEnumFileServiceMap.values()
                .stream()
                .filter(fileService -> fileService.getStorageStrategyEnum() == strategy)
                .findAny().orElse(null);
    }


}
