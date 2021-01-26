package com.chudichen.chufile.service.base;

import com.chudichen.chufile.cache.ChuFileCache;
import com.chudichen.chufile.exception.InitializeDriveException;
import com.chudichen.chufile.model.dto.FileItemDTO;
import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Slf4j
public abstract class AbstractBaseFileService implements BaseFileService {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    private ChuFileCache chuFileCache;

    /**
     * 下载链接过期时间，目前只在兼容S3协议的存储策略中使用到
     */
    @Value("${chu.file.cache.timeout:10}")
    protected Long timeout;

    /**
     * 是否初始化成功
     */
    protected Boolean isInitialized = false;

    /**
     * 基路径
     */
    protected String basePath;

    /**
     * 驱动器id
     */
    public Integer driveId;

    /**
     * 初始化方法，启动时自动调用实现类的此方法进行初始化
     *
     * @param driveId 驱动器id
     */
    public abstract void init(Integer driveId);

    /**
     * 获取当前实现类的存储策略类型
     *
     * @return 存储策略类型枚举对象
     */
    public abstract StorageStrategyEnum getStorageStrategyEnum();

    /**
     * 获取初始化当前存储策略，锁需要的参数信息（用于表单填写）
     *
     * @return 初始化所需要的参数列表
     */
    public abstract List<StorageConfig> storageStrategyConfigList();

    /**
     * 获取单个文件信息
     *
     * @param path 文件路径
     * @return 单个文件的内容
     */
    public abstract FileItemDTO getFileItem(String path);

    /**
     * 清理当前存储粗略的缓存
     */
    public void clearFileCache() {
        chuFileCache.clear(driveId);
    }

    /**
     * 获取是否初始化成功
     *
     * @return {@code true} 初始化不成功
     */
    public Boolean getIsUnInitialized() {
        return !isInitialized;
    }

    /**
     * 获取是否初始化成功
     *
     * @return {@code true} 初始化成功
     */
    public Boolean getIsInitialized() {
        return isInitialized;
    }

    /**
     * 搜索文件
     *
     * @param name 文件名
     * @return 包含该文件名的所有文件或文件夹
     */
    public List<FileItemDTO> search(String name) {
        return chuFileCache.find(driveId, name);
    }


    /**
     * 测试是否链接成功，会尝试取调用获取根路径的文件，如果没有抛出异常，则认为链接成功，某些存储策略需要复写此方法
     */
    protected void testConnection() {
        try {
            fileList("/");
        } catch (Exception e) {
            throw new InitializeDriveException();
        }
    }

}
