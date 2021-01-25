package com.chudichen.chufile.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
@AllArgsConstructor
public class CacheInfoDTO {

    private Integer cacheCount;
    private Integer hitCount;
    private Integer missCount;
    private Set<String> cacheKeys;
}
