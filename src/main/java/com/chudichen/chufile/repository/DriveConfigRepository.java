package com.chudichen.chufile.repository;

import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 驱动器配置
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Repository
public interface DriveConfigRepository extends JpaRepository<DriveConfig, Integer> {

    /**
     * 根据存储策略获取所有的驱动器
     *
     * @param strategy 存储类型
     * @return 指定类型的存储驱动器
     */
    List<DriveConfig> findByStrategy(StorageStrategyEnum strategy);

    /**
     * 更新指定id的订单数量
     *
     * @param orderNum 订单数量
     * @param id id
     */
    @Modifying
    @Query("update DRIVER_CONFIG set orderNum = :orderNum where id = :id")
    void updateSetOrderNumById(Integer orderNum, Integer id);
}
