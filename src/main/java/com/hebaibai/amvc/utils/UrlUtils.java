package com.hebaibai.amvc.utils;

import lombok.NonNull;

/**
 * 处理url工具类
 *
 * @author hjx
 */
public class UrlUtils {

    private static final String REGEX = "/";

    /**
     * 处理url
     * 去掉连接中多余的"/"
     *
     * @param url
     * @return
     */
    public static String makeUrl(@NonNull String url) {
        String[] split = url.split(REGEX);
        StringBuilder newUrl = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() != 0) {
                newUrl.append(split[i]);
            }
            if (i < split.length - 1) {
                newUrl.append(REGEX);
            }
        }
        return newUrl.toString();
    }
}
