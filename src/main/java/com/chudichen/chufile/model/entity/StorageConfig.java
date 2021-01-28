package com.chudichen.chufile.model.entity;

import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * @author chudichen
 * @date 2021-01-22
 */
@Data
@Entity(name = "STORAGE_CONFIG")
public class StorageConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private StorageStrategyEnum strategy;

    @Column(name = "k")
    private String key;

    private String title;

    @Lob
    private String value;

    private Integer driveId;

    public StorageConfig(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public StorageConfig() {
    }
}
