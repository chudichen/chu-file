package com.chudichen.chufile.context;

import com.alibaba.fastjson.JSON;
import com.chudichen.chufile.exception.InvalidDriveException;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.service.DriveConfigService;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import com.chudichen.chufile.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每个驱动器对应一个Service，其中初始化好了与对象存储的连接信息
 * 此驱动器上下文环境用户缓存每个Service，避免重复创建链接
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Slf4j
@Component
@DependsOn("springContextHolder")
public class DriveContext implements ApplicationContextAware {

    private static Map<Integer, AbstractBaseFileService> drivesServiceMap = new ConcurrentHashMap<>();

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private DriveConfigService driveConfigService;


    /**
     * 项目启动时，自动调用数据库存储的驱动器进程初始化
     *
     * @param applicationContext 上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        List<DriveConfig> list = driveConfigService.list();
        list.forEach(driveConfig -> {
            try {
                init(driveConfig.getId());
                log.info("启动时初始化驱动器成功, 驱动器信息: {}", JSON.toJSONString(driveConfig));
            } catch (Exception e) {
                log.error("启动时初始化驱动器失败, 驱动器信息: {}", JSON.toJSONString(driveConfig), e);
            }
        });
    }

    /**
     * 初始化指定驱动器的Service，添加到上下文中
     *
     * @param driveId 驱动器id
     */
    public void init(Integer driveId) {
        AbstractBaseFileService baseFileService = getBeanByDriveId(driveId);
        if (baseFileService == null) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("尝试初始化驱动器, driveId: {}", driveId);
        }
        baseFileService.init(driveId);
        if (log.isDebugEnabled()) {
            log.debug("初始化驱动器成功, driveId: {}", driveId);
        }
        drivesServiceMap.put(driveId, baseFileService);
    }

    /**
     * 获取指定驱动器的service
     *
     * @param driveId 驱动器id
     * @throws InvalidDriveException 到不到驱动器
     * @return 驱动器对应的service
     */
    public AbstractBaseFileService get(Integer driveId) throws InvalidDriveException {
        AbstractBaseFileService abstractBaseFileService = drivesServiceMap.get(driveId);
        if (abstractBaseFileService == null) {
            throw new InvalidDriveException("此驱动器不存在或初始化失败, 请检查后台参数配置");
        }
        return abstractBaseFileService;
    }

    /**
     * 销毁指定驱动器的service
     *
     * @param driveId 驱动器id
     */
    public void destroy(Integer driveId) {
        if (log.isDebugEnabled()) {
            log.debug("清理驱动器上下文对象，driveId：　{}", driveId);
        }
        drivesServiceMap.remove(driveId);
    }

    /**
     * 获取指定驱动器对应的service,状态为未初始化
     *
     * @param driveId　驱动地id
     * @return 驱动器对应未初始化的service
     */
    private AbstractBaseFileService getBeanByDriveId(Integer driveId) {
        StorageStrategyEnum storageStrategyEnum = driveConfigService.findStorageTypeById(driveId);
        Map<String, AbstractBaseFileService> beansOfType = SpringContextHolder.getBeansOfType(AbstractBaseFileService.class);
        return beansOfType.values().stream()
                .filter(abstractBaseFileService -> Objects.equals(abstractBaseFileService.getStorageStrategyEnum(), storageStrategyEnum))
                .findAny()
                .orElse(null);
    }

}
