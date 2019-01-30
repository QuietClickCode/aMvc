package com.hebaibai.amvc.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hjx
 */
public class ClassUtils {

    /**
     * 基本数据类型
     */
    final static Map<String, Class> baseTypeClass = new HashMap<>();

    static {
        baseTypeClass.put(int.class.getName(), int.class);
        baseTypeClass.put(long.class.getName(), long.class);
        baseTypeClass.put(float.class.getName(), float.class);
        baseTypeClass.put(double.class.getName(), double.class);
        baseTypeClass.put(short.class.getName(), short.class);
        baseTypeClass.put(byte.class.getName(), byte.class);
        baseTypeClass.put(boolean.class.getName(), boolean.class);
        baseTypeClass.put(char.class.getName(), char.class);
    }

    /**
     * 找到基本的数据类型Class
     *
     * @param className
     * @return
     */
    public static Class getBaseClassByName(String className) {
        return baseTypeClass.get(className);
    }

    /**
     * 获取class
     *
     * @param className
     * @return
     */
    public static Class forName(String className) {
        Assert.notNull(className);
        try {
            Class<?> aClass = Class.forName(className);
            return aClass;
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取class中的一个方法
     *
     * @param aClass
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class aClass, String methodName, Class[] parameterTypes) {
        Assert.notNull(aClass);
        Assert.notNull(methodName);
        Assert.notNull(parameterTypes);
        try {
            Method aClassMethod = aClass.getMethod(methodName, parameterTypes);
            return aClassMethod;
        } catch (NoSuchMethodException e) {
        }
        return null;
    }
}
