package com.hebaibai.amvc;

import java.lang.reflect.Method;

/**
 * 用于从方法中获取参数名称
 * @author hjx
 */
public interface ParamNameGetter {

    /**
     * 获取方法名称，数组中的参数顺序要和方法定义的顺序相一致
     *
     * @param method
     * @return
     */
    String[] getParamNames(Method method);
}
