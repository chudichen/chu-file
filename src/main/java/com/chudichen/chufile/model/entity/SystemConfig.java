package com.chudichen.chufile.model.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 系统配置
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Data
@Entity(name = "SYSTEM_CONFIG")
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "k")
    private String key;

    @Lob
    private String value;

    private String remark;
}
