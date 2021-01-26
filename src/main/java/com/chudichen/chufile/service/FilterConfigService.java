package com.chudichen.chufile.service;

import cn.hutool.core.util.StrUtil;
import com.chudichen.chufile.model.entity.FilterConfig;
import com.chudichen.chufile.repository.FilterConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-26
 */
@Slf4j
@Service
public class FilterConfigService {

    private final FilterConfigRepository filterConfigRepository;

    public FilterConfigService(FilterConfigRepository filterConfigRepository) {
        this.filterConfigRepository = filterConfigRepository;
    }

    /**
     * 通过driveId查询过滤器
     *
     * @param driveId 驱动器id
     * @return 过滤器
     */
    public List<FilterConfig> findByDriveId(Integer driveId) {
        return filterConfigRepository.findByDriveId(driveId);
    }

    /**
     * 重新保存过滤器
     *
     * @param filterConfigList 过滤器
     * @param driveId 驱动器id
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<FilterConfig> filterConfigList, Integer driveId) {
        filterConfigRepository.deleteByDriveId(driveId);
        filterConfigRepository.saveAll(filterConfigList);
    }

    /**
     * 指定驱动器下的文件名，根据过滤器表达式判断你是否会显示，如果符合任意一条表达式，则不显示，反之则显示
     *
     * @param driveId 驱动器id
     * @param fileName 文件名
     * @return 是否显示
     */
    public boolean filterResultIsHidden(Integer driveId, String fileName) {
        List<FilterConfig> filterConfigList = findByDriveId(driveId);
        for (FilterConfig filterConfig : filterConfigList) {
            String expression = filterConfig.getExpression();
            if (StrUtil.isEmpty(expression)) {
                return false;
            }

            try {
                PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + expression);
                boolean match = pathMatcher.matches(Paths.get(fileName));
                if (match) {
                    return true;
                }
                log.debug("regex: {}, name: {}, contains: {}", expression, fileName, match);
            } catch (Exception e) {
                log.debug("regex: {}, name {}, parse error, skip expression", expression, fileName);
            }
        }
        return false;
    }


}
