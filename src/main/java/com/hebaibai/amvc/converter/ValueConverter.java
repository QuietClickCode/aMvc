package com.hebaibai.amvc.converter;

/**
 * 方法入参转换接口
 *
 * @author hjx
 */
public interface ValueConverter<T> {

    /**
     * 数据转换
     *
     * @param value
     * @param valueClass
     * @return
     */
    T converter(Object value, Class<T> valueClass);
}