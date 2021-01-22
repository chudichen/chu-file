package com.chudichen.chufile.repository;

import com.chudichen.chufile.entity.FilterConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 过滤器
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Repository
public interface FilterConfigRepository extends JpaRepository<FilterConfig, Integer> {

    /**
     * 获取驱动器下所有规则
     *
     * @param driveId 驱动id
     * @return 过滤规则
     */
    List<FilterConfig> findByDriveId(Integer driveId);

    /**
     * 根据驱动id删除所有规则
     *
     * @param driveId 驱动id
     */
    void deleteByDriveId(Integer driveId);
}
