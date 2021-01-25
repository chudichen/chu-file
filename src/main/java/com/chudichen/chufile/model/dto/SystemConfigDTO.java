package com.chudichen.chufile.model.dto;

import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.model.enums.StorageStrategyEnumSerializerConvert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;

/**
 * 系统设置传输类
 *
 * @author chudichen
 * @date 2021-01-25
 */
@Data
@ToString
public class SystemConfigDTO {

    @JsonIgnore
    private Integer id;

    private String siteName;

    private String username;

    @JsonSerialize(using = StorageStrategyEnumSerializerConvert.class)
    private StorageStrategyEnum storageStrategy;

    @JsonIgnore
    private String password;

    private String domain;

    private String customJs;

    private String customCss;

    private String customSize;

    private String tableSize;

    private Boolean showOperator;

    private Boolean showDocument;

    private String announcement;

    private Boolean showAnnouncement;

    private String layout;


}
