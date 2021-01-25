package com.chudichen.chufile.repository;

import com.chudichen.chufile.model.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统配置类
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {

    /**
     * 查找系统设置中，某个设置项对应的值
     *
     * @param key 设置项
     * @return 设置值
     */
    SystemConfig findByKey(String key);
}
