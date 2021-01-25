package com.chudichen.chufile.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriveCacheKey {

    private Integer driveId;
    private String key;
}
