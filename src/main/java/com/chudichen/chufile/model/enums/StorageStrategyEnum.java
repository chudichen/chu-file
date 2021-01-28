package com.chudichen.chufile.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 存储策略，默认为LOCAL
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StorageStrategyEnum {

    /** 本地存储，默认策略 */
    LOCAL("local", "本地存储"),
    /** 阿里云OSS */
    ALIYUN("aliyun", "阿里云 OSS"),
    /** 腾讯云COS */
    TENCENT("tencent", "腾讯云 COS"),
    /** 又拍云 USS */
    UPYUN("upyun", "又拍云 USS"),
    /** FTP */
    FTP("ftp", "FTP"),
    /** UFile */
    UFILE("ufile", "UFile"),
    /** 华为云OBS */
    HUAWEI("huawei", "华为云 OBS"),
    /** MINIO */
    MINIO("minio", "MINIO"),
    /** S3通用协议 */
    S3("s3", "S3通用协议"),
    /** One Drive */
    ONE_DRIVE("onedrive", "OneDrive"),
    /** One Drive 世纪互联 */
    ONE_DRIVE_CHINA("onedrive-china", "OneDrive 世纪互联"),
    /** 七牛云 */
    QINIU("qiniu", "七牛云 KODO");

    private String key;
    private String description;

    StorageStrategyEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

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
