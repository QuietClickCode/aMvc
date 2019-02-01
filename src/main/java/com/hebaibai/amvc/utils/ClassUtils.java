package com.hebaibai.amvc.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassUtils
 *
 * @author hjx
 */
public class ClassUtils {

    /**
     * java 基本类型
     */
    public static List<Class> JAVA_CLASS_LIST = new ArrayList<>();

    public final static Class INT_CLASS = int.class;
    public final static Class LONG_CLASS = long.class;
    public final static Class FLOAT_CLASS = float.class;
    public final static Class DOUBLE_CLASS = double.class;
    public final static Class SHORT_CLASS = short.class;
    public final static Class BYTE_CLASS = byte.class;
    public final static Class BOOLEAN_CLASS = boolean.class;
    public final static Class CHAR_CLASS = char.class;
    public final static Class STRING_CLASS = String.class;
    public final static Class INT_WRAP_CLASS = Integer.class;
    public final static Class LONG_WRAP_CLASS = Long.class;
    public final static Class FLOAT_WRAP_CLASS = Float.class;
    public final static Class DOUBLE_WRAP_CLASS = Double.class;
    public final static Class SHORT_WRAP_CLASS = Short.class;
    public final static Class BOOLEAN_WRAP_CLASS = Boolean.class;
    public final static Class BYTE_WRAP_CLASS = Byte.class;
    public final static Class CHAR_WRAP_CLASS = Character.class;

    static {
        //基本数据类型
        JAVA_CLASS_LIST.add(INT_CLASS);
        JAVA_CLASS_LIST.add(LONG_CLASS);
        JAVA_CLASS_LIST.add(FLOAT_CLASS);
        JAVA_CLASS_LIST.add(DOUBLE_CLASS);
        JAVA_CLASS_LIST.add(SHORT_CLASS);
        JAVA_CLASS_LIST.add(BYTE_CLASS);
        JAVA_CLASS_LIST.add(BOOLEAN_CLASS);
        JAVA_CLASS_LIST.add(CHAR_CLASS);
        //基本数据类型（对象）
        JAVA_CLASS_LIST.add(STRING_CLASS);
        JAVA_CLASS_LIST.add(INT_WRAP_CLASS);
        JAVA_CLASS_LIST.add(LONG_WRAP_CLASS);
        JAVA_CLASS_LIST.add(FLOAT_WRAP_CLASS);
        JAVA_CLASS_LIST.add(DOUBLE_WRAP_CLASS);
        JAVA_CLASS_LIST.add(SHORT_WRAP_CLASS);
        JAVA_CLASS_LIST.add(BOOLEAN_WRAP_CLASS);
        JAVA_CLASS_LIST.add(BYTE_WRAP_CLASS);
        JAVA_CLASS_LIST.add(CHAR_WRAP_CLASS);
    }

    /**
     * 找到基本的数据类型Class
     *
     * @param className
     * @return
     */
    public static Class getBaseClassByName(String className) {
        for (Class aClass : JAVA_CLASS_LIST) {
            if (aClass.getName().equals(className)) {
                return aClass;
            }
        }
        return null;
    }

    /**
     * 检查是否是基本数据类型（包括基本数据类型的包装类）
     *
     * @param aClass
     * @return
     */
    public static boolean isBaseClass(Class aClass) {
        int indexOf = JAVA_CLASS_LIST.indexOf(aClass);
        return indexOf != -1;
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
