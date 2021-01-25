package com.chudichen.chufile.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 过滤器配置
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Data
@Entity(name = "FILTER_CONFIG")
public class FilterConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer driveId;

    private String expression;
}
