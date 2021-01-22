package com.chudichen.chufile.util;

import cn.hutool.core.util.ObjectUtil;
import com.chudichen.chufile.model.constant.ChuFileConstant;

/**
 * 字符串工具类
 *
 * @author chudichen
 * @date 2021-01-21
 */
public class StringUtils {

    private static final char DELIMITER = '/';
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";


    /**
     * 移除URL中第一个'/'
     *
     * @param path 路径
     * @return 路径
     */
    public static String removeFirstSeparator(String path) {
        if (!"".equals(path) && path.charAt(0) == DELIMITER) {
            path = path.substring(1);
        }
        return path;
    }

    public static String concatUrl(String path, String name) {
        return removeDuplicateSeparator(DELIMITER + path + DELIMITER + name);
    }

    /**
     * 将域名和路径组成URL，主要用来处理分隔符'/'
     *
     * @param domain 域名
     * @param path 路径
     * @return URL
     */
    public static String concatPath(String domain, String path) {
        if (path != null && path.length() > 1 && path.charAt(0) != DELIMITER) {
            path = DELIMITER + path;
        }

        if (domain != null && domain.charAt(domain.length() - 1) == DELIMITER) {
            domain = domain.substring(0, domain.length() - 2);
        }

        return domain + path;
    }

    public static String removeDuplicateSeparator(String path) {
        if (path == null || path.length() < 2) {
            return path;
        }

        StringBuilder sb = new StringBuilder();
        if (path.indexOf(HTTP_PROTOCOL) == 0) {
            sb.append(HTTP_PROTOCOL);
        } else if (path.indexOf(HTTPS_PROTOCOL) == 0) {
            sb.append(HTTPS_PROTOCOL);
        }

        for (int i = 0; i < path.length() - 1; i++) {
            char current = path.charAt(i);
            char next = path.charAt(i + 1);
            if (!(current == DELIMITER && next == DELIMITER)) {
                sb.append(current);
            }
        }
        sb.append(path.charAt(path.length() - 1));
        return sb.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param s 待判断字符串
     * @return {@code true}为空
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    /**
     * 判断字符串是否为空
     *
     * @param s 待判断字符串
     * @return {@code true}不为空
     */
    public static boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    /**
     * 获取basePath + path的全路径地址
     *
     * @param basePath 基础路径
     * @param path 路径
     * @return 全路径
     */
    public static String getFullPath(String basePath, String path) {
        basePath = ObjectUtil.defaultIfNull(basePath, "");
        path = ObjectUtil.defaultIfNull(path, "");
        return removeDuplicateSeparator(basePath + ChuFileConstant.PATH_SEPARATOR + path);
    }
}
