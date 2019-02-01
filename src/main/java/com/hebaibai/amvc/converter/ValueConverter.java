package com.hebaibai.amvc.converter;

/**
 * 方法入参转换接口
 */
public interface ValueConverter {

    /**
     * 数据转换
     * 数组也是一个class，所以value使用数组类型
     *
     * @param value
     * @param valueClass
     * @return
     */
    <T> T converter(String[] value, Class<T> valueClass);
}