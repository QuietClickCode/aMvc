package com.hebaibai.amvc.utils;

import com.hebaibai.amvc.RequestType;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理url工具类
 *
 * @author hjx
 */
public class UrlUtils {

    public static final String SLASH = "/";

    /**
     * 将url处理成正确的Url
     *
     * @param value
     * @return
     */
    public static String makeUrl(String value) {
        String s = distinctUrlSlash(value);
        if (!s.startsWith(SLASH)) {
            return SLASH + s;
        } else {
            return s;
        }
    }

    /**
     * 处理url
     * 1: 去掉两头的“/”。
     * 2：去掉连接中相邻并重复的“/”,
     *
     * @param url
     * @return
     */
    public static String distinctUrlSlash(@NonNull String url) {
        List<String> list = new ArrayList<>();
        String[] urlSplit = url.split(SLASH);
        for (String s : urlSplit) {
            if (s.length() == 0) {
                continue;
            }
            list.add(s);
        }
        return String.join(SLASH, list);
    }

    /**
     * 获取url描述
     *
     * @param requestType
     * @param url
     * @return
     */
    public static String getUrlDescribe(RequestType requestType, String url) {
        return makeUrl(requestType.name() + ":" + url);
    }


    /**
     * 判断两个url是否匹配
     *
     * @param regexUrl :/url/{name}/get/{type}
     * @param url      :/url/hebaibai/get/xml
     * @return
     */
    public static boolean matchesUrl(String regexUrl, String url) {
        String[] urlSplit = url.split(SLASH);
        String[] realUrlSplit = regexUrl.split(SLASH);
        String pattern = makePattern(regexUrl);
        //如果地址深度不同
        if (urlSplit.length != realUrlSplit.length) {
            return false;
        }
        //正则匹配不通过
        if (!Pattern.matches(pattern, url)) {
            return false;
        }
        return true;
    }

    /**
     * 找到路径中的参数
     *
     * @param regexUrl
     * @param url
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static Map<String, String> getPathParams(String regexUrl, String url) {
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        String pattern = makePattern(regexUrl);
        Pattern compile = Pattern.compile(pattern);
        //取出路径中的参数名称
        Matcher matcher = compile.matcher(regexUrl);
        while (matcher.find()) {
            int count = matcher.groupCount();
            for (int i = 1; i <= count; i++) {
                //去掉
                String group = matcher.group(i).replaceAll("[\\{|\\}]", "");
                keys.add(group);
            }
        }
        //取出路径中的参数
        matcher = compile.matcher(url);
        while (matcher.find()) {
            int count = matcher.groupCount();
            for (int i = 1; i <= count; i++) {
                String group = matcher.group(i);
                values.add(group);
            }
        }
        Map<String, String> pathParams = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            pathParams.put(keys.get(i), values.get(i));
        }
        return pathParams;
    }

    /**
     * 根据用户配置的url获取正则
     *
     * @param regexUrl
     * @return
     */
    private static String makePattern(String regexUrl) {
        String[] urlSplit = regexUrl.split(SLASH);
        List<String> list = new ArrayList<>();
        for (String key : urlSplit) {
            if (key.length() == 0) {
                continue;
            }
            boolean matches = Pattern.matches("\\{.*\\}", key);
            if (matches) {
                list.add("(.*)");
            } else {
                list.add(key);
            }
        }
        return makeUrl(String.join(SLASH, list));
    }

    /**
     * 获取请求中的参数
     *
     * @param request
     * @return
     */
    public static Map<String, String[]> getRequestValues(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        Map<String, String[]> httpValue = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String attrName = parameterNames.nextElement().toString();
            httpValue.put(attrName, request.getParameterValues(attrName));
        }
        return httpValue;
    }

    /**
     * 根据http请求方式获取RequestType
     *
     * @param request
     * @return
     */
    public static RequestType getRequestType(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        if (requestMethod.equalsIgnoreCase(RequestType.GET.name())) {
            return RequestType.GET;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.POST.name())) {
            return RequestType.POST;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.PUT.name())) {
            return RequestType.PUT;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.DELETE.name())) {
            return RequestType.DELETE;
        }
        throw new UnsupportedOperationException("请求方式不支持：" + requestMethod);
    }
}
