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

    private Class objectClass;

    private Method method;

    private String[] paramNames;

    private Class[] paramClasses;

}
