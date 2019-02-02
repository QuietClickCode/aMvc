package com.hebaibai.amvc;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * 一个请求Url到Method的映射
 *
 * @author hjx
 */
public @Data
class UrlMethodMapping {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求类型
     */
    private RequestType[] requestTypes;


    /**
     * method的所属class
     */
    private Class objectClass;

    /**
     * url 对应的method
     */
    private Method method;

    /**
     * method 的入参名称
     * 顺序要保持一致
     */
    private String[] paramNames;

    /**
     * method 的入参类型
     * 顺序要保持一致
     */
    private Class[] paramClasses;


}
