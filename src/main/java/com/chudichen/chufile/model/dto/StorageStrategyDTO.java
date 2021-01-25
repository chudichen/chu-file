package com.chudichen.chufile.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StorageStrategyDTO {

    private String key;

    private String description;

    @JsonProperty(defaultValue = "false")
    private Boolean available;
}
