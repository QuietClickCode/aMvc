package com.hebaibai.amvc;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;

/**
 * 一个请求Url到Method的映射
 *
 * @author hjx
 */
@ToString
@Setter
@Getter
public class UrlMethodMapping {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求类型
     */
    private RequestType[] requestTypes;

    /**
     * 请求方法所属class实例
     */
    private Object object;

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
