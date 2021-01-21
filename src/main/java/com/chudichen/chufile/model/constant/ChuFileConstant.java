package com.chudichen.chufile.model.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 文件常量类
 *
 * @author chudichen
 * @date 2021-01-21
 */
@Configuration
public class ChuFileConstant {

    public static final String USER_HOME = System.getProperty("user.home");

    public static final Character PATH_SEPARATOR_CHAR = '/';

    public static final String PATH_SEPARATOR = "/";

    /**
     * 系统产生的临时文件路径，默认：/.chu-file/tmp2
     */
    public static String TMP_FILE_PATH;

    /**
     * 页面文档文件，默认：readme.md
     */
    public static String README_FILE_NAME;

    /**
     * 密码文件，默认：password.txt
     */
    public static String PASSWORD_FILE_NAME;

    /**
     * 最大支持文件大小为？MB的音乐文件解析封面，歌手等信息,默认：1MB
     */
    public static Long AUDIO_MAX_FILE_SIZE_MB;

    /**
     * 最大支持文本文件大小为？KB的文件内容，默认：100KB
     */
    public static Long TEXT_MAX_FILE_SIZE_KB;

    @Autowired(required = false)
    public void setTmpFilePath(@Value("${chu.file.tmp.path:/.chu-file/tmp2}") String tmpFilePath) {
        ChuFileConstant.TMP_FILE_PATH = tmpFilePath;
    }

    @Autowired(required = false)
    public void setHeaderFileName(@Value("${chu.file.constant.readme:readme.md}") String headerFileName) {
        ChuFileConstant.README_FILE_NAME = headerFileName;
    }

    @Autowired(required = false)
    public void setPasswordFileName(@Value("${chu.file.constant.password:password.txt}") String passwordFileName) {
        ChuFileConstant.PASSWORD_FILE_NAME = passwordFileName;
    }

    @Autowired(required = false)
    public void setAudioMaxFileSizeMb(@Value("${chu.file.preview.text.maxFileSizeMb:1}") Long maxFileSizeMb) {
        ChuFileConstant.AUDIO_MAX_FILE_SIZE_MB = maxFileSizeMb;
    }

    @Autowired(required = false)
    public void setTextMaxFileSizeMb(@Value("${chu.file.preview.text.maxFileSizeKb:100}") Long maxFileSizeKb) {
        ChuFileConstant.TEXT_MAX_FILE_SIZE_KB = maxFileSizeKb;
    }
}
