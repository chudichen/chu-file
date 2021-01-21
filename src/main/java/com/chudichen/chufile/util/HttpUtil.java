package com.chudichen.chufile.util;

import com.chudichen.chufile.exception.PreviewException;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * http工具类
 *
 * @author chudichen
 * @date 2021-01-21
 */
@Slf4j
public class HttpUtil {

    /**
     * 最大支持文件预览大小：1M
     *
     * @param url 文件路径
     * @return 结果,{@code null}标示不预览
     */
    public static String getTextContent(String url) {
        RestTemplate restTemplate = SpringContextHolder.getBean("restTemplate");
        long maxFileSizeKb = ChuFileConstant.AUDIO_MAX_FILE_SIZE_MB * 1024;
        if (getRemoteFileSize(url) > maxFileSizeKb) {
            throw new PreviewException("存储源跨域请求失败，服务器中转状态，预览文件超出大小，最大支持1M");
        }
        String result = restTemplate.getForObject(url, String.class);
        return result == null ? "" : result;
    }

    /**
     * 返回文件大小，单位kb
     *
     * @param url 文件路径
     * @return 大小
     */
    public static Long getRemoteFileSize(String url) {
        long size = 0;
        try {
            URL urlObject = new URL(url);
            URLConnection conn = urlObject.openConnection();
            size = conn.getContentLength();
        } catch (IOException e) {
            log.error("HttpUtil.getRemoteFileSize异常, url: {}", url, e);
        }
        return size;
    }
}
