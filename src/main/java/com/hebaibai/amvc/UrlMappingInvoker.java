package com.hebaibai.amvc;

import com.hebaibai.amvc.objectfactory.ObjectFactory;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 映射中方法的执行者
 */
@Slf4j
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
    public Object invokeMapping(
            @NonNull UrlMethodMapping methodMapping,
            @NonNull Object object,
            @NonNull Object[] methodValues
    ) {
        Method method = methodMapping.getMethod();
        Object invokeResult = null;
        try {
            invokeResult = method.invoke(object, methodValues);
        } catch (IllegalAccessException e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        } catch (InvocationTargetException e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
        return invokeResult;
    }

}
