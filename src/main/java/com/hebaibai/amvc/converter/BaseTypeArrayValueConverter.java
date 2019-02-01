package com.hebaibai.amvc.converter;

import com.hebaibai.amvc.utils.Assert;

/**
 * 基本数据类型的转换
 *
 * @author hjx
 */
public class BaseTypeArrayValueConverter extends BaseTypeValueConverter implements ValueConverter {

    @Override
    public <T> T converter(String[] value, Class<T> valueClass) {
        Assert.notNull(value);
        Assert.notNull(valueClass);
        Assert.isTrue(valueClass.isArray(), "valueClass 必须是数组类型！");
        Class componentType = valueClass.getComponentType();
        Assert.isTrue(!componentType.isArray(), "valueClass 不支持多元数组！");
        Object[] object = new Object[value.length];
        for (int i = 0; i < value.length; i++) {
            object[i] = super.converter(new String[]{value[i]}, componentType);
        }
        return (T) object;
    }
}
