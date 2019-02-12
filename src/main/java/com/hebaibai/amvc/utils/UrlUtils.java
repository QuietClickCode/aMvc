package com.hebaibai.amvc.utils;

import lombok.NonNull;

/**
 * 处理url工具类
 *
 * @author hjx
 */
public class UrlUtils {

    private static final String SLASH = "/";

    /**
     * 处理url
     * 1：去掉连接中相邻并重复的“/”,
     * 2：链接开头没有“/”,则添加。
     * 3：链接结尾有“/”，则去掉。
     *
     * @param url
     * @return
     */
    public static String makeUrl(@NonNull String url) {
        char[] chars = url.toCharArray();
        StringBuilder newUrl = new StringBuilder();
        if (!url.startsWith(SLASH)) {
            newUrl.append(SLASH);
        }
        for (int i = 0; i < chars.length; i++) {
            if (i != 0 && chars[i] == chars[i - 1] && chars[i] == '/') {
                continue;
            }
            if (i == chars.length - 1 && chars[i] == '/') {
                continue;
            }
            newUrl.append(chars[i]);
        }
        return newUrl.toString();
    }
}
