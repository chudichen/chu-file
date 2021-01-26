package com.chudichen.chufile.service.impl;

import com.chudichen.chufile.exception.InitializeDriveException;
import com.chudichen.chufile.exception.NotExistFileException;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import com.chudichen.chufile.model.constant.StorageConfigConstant;
import com.chudichen.chufile.model.constant.SystemConfigConstant;
import com.chudichen.chufile.model.dto.FileItemDTO;
import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.entity.SystemConfig;
import com.chudichen.chufile.model.enums.FileTypeEnum;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.repository.SystemConfigRepository;
import com.chudichen.chufile.service.StorageConfigService;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import com.chudichen.chufile.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 本地存储策略
 *
 * @author chudichen
 * @date 2021-01-26
 */
@Slf4j
@Getter
@Setter
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LocalServiceImpl extends AbstractBaseFileService {

    private final StorageConfigService storageConfigService;
    private final SystemConfigRepository systemConfigRepository;

    private String filePath;

    public LocalServiceImpl(StorageConfigService storageConfigService, SystemConfigRepository systemConfigRepository) {
        this.storageConfigService = storageConfigService;
        this.systemConfigRepository = systemConfigRepository;
    }

    @Override
    public void init(Integer driveId) {
        super.driveId = driveId;
        Map<String, StorageConfig> stringStorageConfigMap = storageConfigService.selectStorageConfigMapByDriveId(driveId);
        filePath = stringStorageConfigMap.get(StorageConfigConstant.FILE_PATH_KEY).getValue();
        if (Objects.isNull(filePath)) {
            log.debug("初始化存储策略 [{}] 失败: 参数不完整", getStorageStrategyEnum().name());
            super.isInitialized = false;
            return;
        }

        File file = new File(filePath);
        if (file.exists()) {
            testConnection();
            super.isInitialized = true;
        } else {
            throw new InitializeDriveException("文件路径: \"" + file.getAbsolutePath() + "\"不存在, 请检查是否填写正确.");
        }
    }

    @Override
    public List<FileItemDTO> fileList(String path) throws FileNotFoundException {
        String fullPath = StringUtils.removeDuplicateSeparator(filePath + path);
        File file = new File(fullPath);
        if (!file.exists()) {
            throw  new FileNotFoundException("文件不存在");
        }

        File[] files = file.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }

        return Stream.of(files).map(f -> {
            FileItemDTO fileItemDTO = new FileItemDTO();
            fileItemDTO.setType(f.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE);
            fileItemDTO.setTime(new Date(f.lastModified()));
            fileItemDTO.setSize(f.length());
            fileItemDTO.setName(f.getName());
            fileItemDTO.setPath(path);
            if (f.isFile()) {
                fileItemDTO.setUrl(getDownloadUrl(StringUtils.concatUrl(path, f.getName())));
            }
            return fileItemDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public String getDownloadUrl(String path) {
        SystemConfig usernameConfig = systemConfigRepository.findByKey(SystemConfigConstant.DOMAIN);
        return StringUtils.removeDuplicateSeparator(usernameConfig.getValue() + "/file/" + driveId + ChuFileConstant.PATH_SEPARATOR + path);
    }

    @Override
    public FileItemDTO getFileItem(String path) {
        String fullPath = StringUtils.concatPath(filePath, path);
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new NotExistFileException();
        }

        FileItemDTO fileItemDTO = new FileItemDTO();
        fileItemDTO.setTime(new Date(file.lastModified()));
        fileItemDTO.setType(file.isDirectory() ? FileTypeEnum.FOLDER : FileTypeEnum.FILE);
        fileItemDTO.setSize(file.length());
        fileItemDTO.setName(file.getName());
        fileItemDTO.setPath(filePath);
        if (file.isFile()) {
            fileItemDTO.setUrl(getDownloadUrl(path));
        }

        return fileItemDTO;
    }

    @Override
    public StorageStrategyEnum getStorageStrategyEnum() {
        return StorageStrategyEnum.LOCAL;
    }

    @Override
    public List<StorageConfig> storageStrategyConfigList() {
        return Collections.singletonList(new StorageConfig("filePath", "文件路径"));
    }
}
