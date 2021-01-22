package com.chudichen.chufile.aspect;

import com.chudichen.chufile.cache.ChuFileCache;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 缓存切面类，用于访问文件夹时，缓存文件列表内容
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Aspect
@Component
public class FileListCacheAspect {

    @Autowired
    private ChuFileCache chuFileCache;

}
