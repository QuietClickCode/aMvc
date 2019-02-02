package com.hebaibai.amvc;

import com.hebaibai.amvc.objectfactory.ObjectFactory;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 映射中方法的执行者
 * @author hjx
 */
public @Data
class UrlMappingInvoker {


    /**
     * 用于根据class实例化对象.
     */
    private ObjectFactory objectFactory;

    /**
     * 执行Url对应的方法
     *
     * @param methodMapping
     * @param object
     * @param methodValues
     * @return
     */
    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
    public Object invokeMapping(
            @NonNull UrlMethodMapping methodMapping,
            @NonNull Object object,
            @NonNull Object[] methodValues
    ) {
        Method method = methodMapping.getMethod();
        Object invokeResult = method.invoke(object, methodValues);
        return invokeResult;
    }

}
