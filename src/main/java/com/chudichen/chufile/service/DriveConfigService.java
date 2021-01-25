package com.chudichen.chufile.service;

import com.alibaba.fastjson.JSONObject;
import com.chudichen.chufile.cache.ChuFileCache;
import com.chudichen.chufile.context.DriveContext;
import com.chudichen.chufile.context.StorageStrategyContext;
import com.chudichen.chufile.exception.InitializeDriveException;
import com.chudichen.chufile.model.constant.StorageConfigConstant;
import com.chudichen.chufile.model.dto.CacheInfoDTO;
import com.chudichen.chufile.model.dto.DriveConfigDTO;
import com.chudichen.chufile.model.dto.StorageStrategyConfig;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.repository.DriveConfigRepository;
import com.chudichen.chufile.repository.StorageConfigRepository;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 驱动service类
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Slf4j
@Service
public class DriveConfigService {

    public static final Class<StorageStrategyConfig> STORAGE_STRATEGY_CONFIG_CLASS = StorageStrategyConfig.class;

    private final DriveConfigRepository driveConfigRepository;
    private final StorageConfigRepository storageConfigRepository;
    private final DriveContext driveContext;
    private final ChuFileCache chuFileCache;


    public DriveConfigService(DriveConfigRepository driveConfigRepository, StorageConfigRepository storageConfigRepository, DriveContext DriveContext, ChuFileCache chuFileCache) {
        this.driveConfigRepository = driveConfigRepository;
        this.storageConfigRepository = storageConfigRepository;
        this.driveContext = DriveContext;
        this.chuFileCache = chuFileCache;
    }

    /**
     * 获取所有驱动器列表
     *
     * @return 驱动器列表
     */
    public List<DriveConfig> list() {
        Sort sort =  Sort.by(Sort.Direction.ASC, "orderNum");
        return driveConfigRepository.findAll(sort);
    }

    /**
     * 获取所有已启用的驱动器列表
     *
     * @return 已启用的驱动器列表
     */
    public List<DriveConfig> listOnlyEnable() {
        DriveConfig driveConfig = new DriveConfig();
        driveConfig.setEnable(true);
        Example<DriveConfig> example = Example.of(driveConfig);
        Sort sort =  Sort.by(Sort.Direction.ASC, "orderNum");
        return driveConfigRepository.findAll(example, sort);
    }

    /**
     * 获取指定的驱动器设置
     *
     * @param id 驱动器id
     * @return 驱动器设置
     */
    public DriveConfig findById(Integer id) {
        return driveConfigRepository.findById(id).orElse(null);
    }

    /**
     * 获取指定驱动器DTO对象，此对象包含详细的参数设置
     *
     * @param id 驱动器id
     * @return {@link DriveConfigDTO}
     */
    public DriveConfigDTO findDriveConfigDTOById(Integer id) {
        DriveConfig driveConfig = driveConfigRepository.getOne(id);
        DriveConfigDTO driveConfigDTO = new DriveConfigDTO();
        BeanUtils.copyProperties(driveConfig, driveConfigDTO);

        List<StorageConfig> storageConfigList = storageConfigRepository.findByDriveId(driveConfig.getId());
        StorageStrategyConfig storageStrategyConfig = new StorageStrategyConfig();
        storageConfigList.forEach(storageConfig -> {
            String key = storageConfig.getKey();
            String value = storageConfig.getValue();

            try {
                Field declaredField = STORAGE_STRATEGY_CONFIG_CLASS.getDeclaredField(key);
                declaredField.setAccessible(true);
                if (Objects.equals(StorageConfigConstant.IS_PRIVATE, key)) {
                    declaredField.set(storageStrategyConfig, Boolean.valueOf(value));
                } else {
                    declaredField.set(storageStrategyConfig, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("通过反射, 将字段 {} 注入 DriveConfigDTO 时出现异常:", key, e);
            }
        });
        driveConfigDTO.setStorageStrategyConfig(storageStrategyConfig);
        return driveConfigDTO;
    }

    /**
     * 获取指定驱动器的存储粗略
     *
     * @param id 驱动器id
     * @return 驱动器对应的存储策略
     */
    public StorageStrategyEnum findStorageTypeById(Integer id) {
        return driveConfigRepository.findById(id).map(DriveConfig::getStrategy).orElse(null);
    }

    /**
     * 新增或保存驱动器设置
     *
     * @param driveConfig 驱动器设置
     * @return 保存后的驱动器
     */
    public DriveConfig saveOrUpdate(DriveConfig driveConfig) {
        return driveConfigRepository.save(driveConfig);
    }

    /**
     * 保存驱动器基本信息及其对应的参数设置
     *
     * @param driveConfigDTO 驱动器 DTO 对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDriveConfigDTO(DriveConfigDTO driveConfigDTO) {
        // 保存基本信息
        DriveConfig driveConfig = new DriveConfig();
        StorageStrategyEnum strategy = driveConfigDTO.getStrategy();
        BeanUtils.copyProperties(driveConfigDTO, driveConfig);
        driveConfigRepository.save(driveConfig);

        // 保存存储策略设置
        StorageStrategyConfig storageStrategyConfig = driveConfigDTO.getStorageStrategyConfig();
        AbstractBaseFileService storageStrategyService = StorageStrategyContext.getStorageStrategyService(strategy);

        // 判断是新增还是修改
        boolean createFlag = driveConfigDTO.getId() == null;
        List<StorageConfig> storageConfigList;
        if (createFlag) {
            storageConfigList = storageStrategyService.storageStrategyConfigList();
        } else {
            storageConfigList = storageConfigRepository.findByDriveId(driveConfigDTO.getId());
        }

        storageConfigList.forEach(storageConfig -> {
            String key = storageConfig.getKey();

            try {
                Field field = STORAGE_STRATEGY_CONFIG_CLASS.getDeclaredField(key);
                field.setAccessible(true);
                Object o = field.get(storageStrategyConfig);
                String value = o == null ? null : o.toString();
                storageConfig.setValue(value);
                storageConfig.setStrategy(strategy);
                storageConfig.setDriveId(driveConfig.getId());
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.error("通过反射, 从 StorageStrategyConfig 中获取字段 {} 时出现异常:", key, e);
            }
        });

        storageConfigRepository.saveAll(storageConfigList);
        driveContext.init(driveConfig.getId());

        AbstractBaseFileService driveService = driveContext.get(driveConfig.getId());
        if (driveService.getIsUnInitialized()) {
            throw new InitializeDriveException("初始化异常，请检查配置是否正确");
        }

        if (driveConfig.getAutoRefreshCache() || !createFlag) {
            startAutoCacheRefresh(driveConfig.getId());
        }
    }

    /**
     * 删除指定驱动器设置，会级联删除其参数设置
     *
     * @param id 驱动器id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("尝试删除驱动器， driveId： {}", id);
        }
        DriveConfig driveConfig = driveConfigRepository.getOne(id);
        driveConfigRepository.deleteById(id);
        storageConfigRepository.deleteByDriveId(id);
        if (driveConfig.getEnableCache()) {
            chuFileCache.stopAutoCacheRefresh(id);
            chuFileCache.clear(id);
        }

        driveContext.destroy(id);
        if (log.isDebugEnabled()) {
            log.debug("尝试删除驱动器成功，已清理相关数据，driveId：{}", id);
        }
    }

    /**
     * 根据存储策略类型获取所有驱动器
     *
     * @param strategy 存储类型
     * @return 指定存储类型的驱动器
     */
    public List<DriveConfig> findByStrategy(StorageStrategyEnum strategy) {
        return driveConfigRepository.findByStrategy(strategy);
    }

    /**
     * 更新指定驱动器的缓存启用状态
     *
     * @param driveId 驱动器Id
     * @param cacheEnable 是否启用缓存
     */
    public void updateCacheStatus(Integer driveId, Boolean cacheEnable) {
        DriveConfig driveConfig = findById(driveId);
        if (driveConfig == null) {
            return;
        }
        driveConfig.setEnableCache(cacheEnable);
        driveConfigRepository.save(driveConfig);
    }

    /**
     * 更新指定驱动器的缓存启用状态
     *
     * @param driveId 驱动器id
     * @param autoRefreshCache 是否启用缓存自动刷新
     */
    public void updateAutoRefreshCacheStatus(Integer driveId, Boolean autoRefreshCache) {
        DriveConfig driveConfig = findById(driveId);
        if (driveConfig == null) {
            return;
        }
        driveConfig.setAutoRefreshCache(autoRefreshCache);
        driveConfigRepository.save(driveConfig);
    }

    /**
     * 获取指定驱动器的缓存信息
     *
     * @param driveId 驱动器id
     * @return 缓存信息
     */
    public CacheInfoDTO findCacheInfo(Integer driveId) {
        int hitCount = chuFileCache.getHitCount(driveId);
        int missCount = chuFileCache.getMissCount(driveId);
        Set<String> keys = chuFileCache.keySet(driveId);
        int cacheCount = keys.size();
        return new CacheInfoDTO(cacheCount, hitCount, missCount, keys);
    }

    /**
     * 刷新指定key的缓存
     * 1. 情况此key的缓存
     * 2. 重新调动方法写入缓存
     *
     * @param driveId 驱动器id
     * @param key 缓存key（文件夹名称）
     * @throws Exception 异常
     */
    public void refreshCache(Integer driveId, String key) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("手动刷新缓存，driveId： {}， key: {}", driveId, key);
        }
        chuFileCache.remove(driveId, key);
        AbstractBaseFileService baseFileService = driveContext.get(driveId);
        baseFileService.fileList(key);
    }

    /**
     * 开启缓存自动刷新
     *
     * @param driveId 驱动器id
     */
    public void startAutoCacheRefresh(Integer driveId) {
        DriveConfig driveConfig = findById(driveId);
        driveConfig.setAutoRefreshCache(true);
        driveConfigRepository.save(driveConfig);
        chuFileCache.startAutoCacheRefresh(driveId);
    }

    /**
     * 停止缓存自动刷新
     *
     * @param   driveId 驱动器id
     */
    public void stopAutoCacheRefresh(Integer driveId) {
        DriveConfig driveConfig = findById(driveId);
        driveConfig.setAutoRefreshCache(false);
        driveConfigRepository.save(driveConfig);
        chuFileCache.stopAutoCacheRefresh(driveId);
    }

    /**
     * 清理缓存
     *
     * @param   driveId 驱动器id
     */
    public void clearCache(Integer driveId) {
        chuFileCache.clear(driveId);
    }

    /**
     * 交换驱动器排序
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveDriveDrag(List<JSONObject> driveConfigs) {
        for (int i = 0; i < driveConfigs.size(); i++) {
            JSONObject item = driveConfigs.get(i);
            driveConfigRepository.updateSetOrderNumById(i, item.getInteger("id"));
        }
    }
}
