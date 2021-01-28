package com.chudichen.chufile.model.dto;

import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.model.enums.StorageStrategyEnumJsonDeserializerConvert;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
public class DriveConfigDTO {

    private Integer id;
    private String name;

    @JsonDeserialize(using = StorageStrategyEnumJsonDeserializerConvert.class)
    private StorageStrategyEnum type;

    private Boolean enable;

    private Boolean enableCache;

    private Boolean autoRefreshCache;

    private Boolean searchEnable;

    private Boolean searchIgnoreCase;

    private Boolean searchContainEncryptedFile;

    private Integer orderNum;

    private StorageStrategyConfig storageStrategyConfig;

}
