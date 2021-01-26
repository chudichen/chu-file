package com.chudichen.chufile.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.SecureUtil;
import com.chudichen.chufile.cache.ChuFileCache;
import com.chudichen.chufile.exception.InvalidDriveException;
import com.chudichen.chufile.model.constant.SystemConfigConstant;
import com.chudichen.chufile.model.dto.SystemConfigDTO;
import com.chudichen.chufile.model.dto.SystemFrontConfigDTO;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.entity.SystemConfig;
import com.chudichen.chufile.repository.SystemConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-26
 */
@Slf4j
@Service
public class SystemConfigService {

    private final ChuFileCache chuFileCache;
    private final SystemConfigRepository systemConfigRepository;
    private final DriveConfigService driveConfigService;
    private final Class<SystemConfigDTO> systemConfigClass = SystemConfigDTO.class;

    public SystemConfigService(ChuFileCache chuFileCache,
                               SystemConfigRepository systemConfigRepository,
                               DriveConfigService driveConfigService) {
        this.chuFileCache = chuFileCache;
        this.systemConfigRepository = systemConfigRepository;
        this.driveConfigService = driveConfigService;
    }

    /**
     * 获取系统设置，如果缓存有则从缓存取，如果缓存没有则去数据库去并写入缓存
     *
     * @return 系统设置
     *
     */
    public SystemConfigDTO getSystemConfig() {
        SystemConfigDTO cacheConfig = chuFileCache.getConfig();
        if (cacheConfig != null) {
            return cacheConfig;
        }

        SystemConfigDTO systemConfigDTO = new SystemConfigDTO();
        List<SystemConfig> systemConfigList = systemConfigRepository.findAll();
        systemConfigList.forEach(systemConfig -> {
            String key = systemConfig.getKey();

            try {
                Field field = systemConfigClass.getDeclaredField(key);
                field.setAccessible(true);
                String strVal = systemConfig.getValue();
                Object convertVal = Convert.convert(field.getType(), strVal);
                field.set(systemConfigDTO, convertVal);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("通过反射, 将字段 {} 注入 SystemConfigDTO 时出现异常:", key, e);
            }
        });
        chuFileCache.updateConfig(systemConfigDTO);
        return systemConfigDTO;
    }

    /**
     * 更新系统设置，并清空缓存中的内容
     *
     * @param systemConfigDTO 系统
     */
    public void updateSystemConfig(SystemConfigDTO systemConfigDTO) {
        List<SystemConfig> systemConfigList = new ArrayList<>();
        Field[] fields = systemConfigClass.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            SystemConfig systemConfig = systemConfigRepository.findByKey(key);
            if (systemConfig != null) {
                field.setAccessible(true);
                Object val = null;

                try {
                    val = field.get(systemConfigDTO);
                } catch (IllegalAccessException e) {
                    log.error("通过反射, 从 SystemConfigDTO 获取字段 {}  时出现异常:", key, e);
                }

                if (val != null) {
                    systemConfig.setValue(val.toString());
                    systemConfigList.add(systemConfig);
                }
            }
        }

        chuFileCache.removeConfig();
        systemConfigRepository.saveAll(systemConfigList);
    }

    /**
     * 根据驱动器id，获取对于前台页面的系统设置
     *
     * @param driveId 驱动器id
     * @return 前台系统设置
     */
    public SystemFrontConfigDTO getSystemFrontConfig(Integer driveId) {
        SystemConfigDTO systemConfig = getSystemConfig();
        SystemFrontConfigDTO systemFrontConfigDTO = new SystemFrontConfigDTO();
        BeanUtils.copyProperties(systemConfig, systemFrontConfigDTO);

        DriveConfig driveConfig = driveConfigService.findById(driveId);
        if (driveConfig == null) {
            throw new InvalidDriveException("此驱动器不存在或初始化失败, 请检查后台参数配置");
        }
        systemFrontConfigDTO.setSearchEnable(driveConfig.getSearchEnable());
        return systemFrontConfigDTO;
    }

    /**
     * 更新后台账号密码
     *
     * @param username 用户名
     * @param password 密码
     */
    public void updateUsernameAndPwd(String username, String password) {
        SystemConfig usernameConfig = systemConfigRepository.findByKey(SystemConfigConstant.USERNAME);
        usernameConfig.setValue(username);
        systemConfigRepository.save(usernameConfig);

        String encryptionPassword = SecureUtil.md5(password);
        SystemConfig systemConfig = systemConfigRepository.findByKey(SystemConfigConstant.PASSWORD);
        systemConfig.setValue(encryptionPassword);

        chuFileCache.removeConfig();
        systemConfigRepository.save(systemConfig);
    }

    /**
     * 获取管理员名称
     *
     * @return  管理员名称
     */
    public String getAdminUsername() {
        SystemConfigDTO systemConfigDTO = getSystemConfig();
        return systemConfigDTO.getUsername();
    }
}
