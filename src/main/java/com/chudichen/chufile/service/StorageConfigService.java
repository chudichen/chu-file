package com.chudichen.chufile.service;

import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.repository.StorageConfigRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chudichen
 * @date 2021-01-26
 */
@Service
public class StorageConfigService {

    private final StorageConfigRepository storageConfigRepository;

    public StorageConfigService(StorageConfigRepository storageConfigRepository) {
        this.storageConfigRepository = storageConfigRepository;
    }

    public List<StorageConfig> selectStorageConfigByStrategy(StorageStrategyEnum strategy) {
        return storageConfigRepository.findByStrategy(strategy);
    }

    public List<StorageConfig> selectStorageConfigByDriveId(Integer driveId) {
        return storageConfigRepository.findByDriveId(driveId);
    }

    public StorageConfig findByDriveIdAndKey(Integer driveId, String key) {
        return storageConfigRepository.findByDriveIdAndKey(driveId, key);
    }

    public Map<String, StorageConfig> selectStorageConfigMapByKey(StorageStrategyEnum strategy) {
        Map<String, StorageConfig> map = new HashMap<>(24);
        for (StorageConfig storageConfig : selectStorageConfigByStrategy(strategy)) {
            map.put(storageConfig.getKey(), storageConfig);
        }
        return map;
    }

    /**
     * 通过driveId获取系统配置
     *
     * @param driveId 驱动器id
     * @return 系统配置
     */
    public Map<String, StorageConfig> selectStorageConfigMapByDriveId(Integer driveId) {
        Map<String, StorageConfig> map = new HashMap<>(24);
        for (StorageConfig storageConfig : selectStorageConfigByDriveId(driveId)) {
            map.put(storageConfig.getKey(), storageConfig);
        }
        return map;
    }

    public void updateStorageConfig(List<StorageConfig> storageConfigList) {
        storageConfigRepository.saveAll(storageConfigList);
    }
}
