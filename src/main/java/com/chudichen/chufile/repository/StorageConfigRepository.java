package com.chudichen.chufile.repository;

import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 存储配置类
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Repository
public interface StorageConfigRepository extends JpaRepository<StorageConfig, Integer> {

    /**
     * 根据存储类型找对应的配置信息
     *
     * @param strategy 策略
     * @return 此类型所有的配置信息
     */
    List<StorageConfig> findByStrategy(StorageStrategyEnum strategy);

    /**
     * 通过驱动器id找到对应的配置信息
     *
     * @param driveId 驱动器id
     * @return 此驱动器所有的配置信息
     */
    List<StorageConfig> findByDriveId(Integer driveId);

    /**
     * 删除指定驱动器对应的配置信息
     *
     * @param driveId 驱动器id
     */
    void deleteByDriveId(Integer driveId);

    /**
     * 查找某个驱动器的某个KEY的值
     *
     * @param driveId 驱动器
     * @param key 值
     * @return KEY对应的对象
     */
    StorageConfig findByDriveIdAndKey(Integer driveId, String key);
}
