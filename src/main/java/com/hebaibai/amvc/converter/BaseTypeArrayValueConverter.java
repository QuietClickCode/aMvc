package com.hebaibai.amvc.converter;

import com.hebaibai.amvc.utils.Assert;

/**
 * 基本数据类型的转换
 *
 * @author hjx
 */
public class BaseTypeArrayValueConverter implements ValueConverter<Object[]> {

    /**
     * 基础类型的转换工具
     */
    private BaseTypeValueConverter converter = new BaseTypeValueConverter();

    @Override
    public Object[] converter(Object value, Class valueClass) {
        Assert.isTrue(valueClass.isArray(), "valueClass 必须是数组类型！");
        String[] strings = null;
        try {
            strings = (String[]) value;
        } catch (Exception e) {
            Assert.isTrue(false, "value 必须是一个数组！");
        }
        Class componentType = valueClass.getComponentType();
        Assert.isTrue(!componentType.isArray(), "valueClass 不支持多元数组！");
        Object[] objects = new Object[strings.length];
        for (int i = 0; i < strings.length; i++) {
            objects[i] = converter.converter(strings[i], componentType);
        }
        return objects;
    }
}
