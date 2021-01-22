package com.chudichen.chufile.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 存储策略，默认为LOCAL
 *
 * @author chudichen
 * @date 2021-01-22
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StorageStrategyEnum {

    /** 本地存储，默认策略 */
    LOCAL,
    /** 阿里云OSS */
    ALIYUN,
    /** 腾讯云COS */
    TENCENT,
    /** 又拍云 USS */
    UPYUN,
    /** FTP */
    FTP,
    /** UFile */
    UFILE,
    /** 华为云OBS */
    HUAWEI,
    /** MINIO */
    MINIO,
    /** S3通用协议 */
    S3,
    /** One Drive */
    ONE_DRIVE,
    /** One Drive 世纪互联 */
    ONE_DRIVE_CHINA,
    /** 七牛云 */
    QINIU;

    /**
     * 根据名字指定存储策略，找不到则使用LOCAL策略
     *
     * @param value 策略
     * @return 策略
     */
    public static StorageStrategyEnum getEnum(String value) {
        for (StorageStrategyEnum storageStrategyEnum : StorageStrategyEnum.values()) {
            if (storageStrategyEnum.name().equalsIgnoreCase(value)) {
                return storageStrategyEnum;
            }
        }
        return LOCAL;
    }
}
