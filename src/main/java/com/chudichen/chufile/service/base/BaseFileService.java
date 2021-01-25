package com.chudichen.chufile.service.base;

import com.chudichen.chufile.model.dto.FileItemDTO;

import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public interface BaseFileService {

    /**
     * 获取指定路径下的文件及文件夹
     *
     * @param path 文件路径
     * @return 文件及文件夹列表
     * @throws Exception 获取文件列表中出现的异常
     */
    List<FileItemDTO> fileList(String path) throws Exception;

    /**
     * 获取文件下载地址
     *
     * @param path 文件路径
     * @return 文件下载地址
     */
    String getDownloadUrl(String path);
}
