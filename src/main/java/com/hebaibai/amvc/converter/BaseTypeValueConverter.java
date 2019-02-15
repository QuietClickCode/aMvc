package com.hebaibai.amvc.converter;

import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.ClassUtils;
import lombok.NonNull;

/**
 * 基本数据类型的转换
 *
 * @author hjx
 */
public class BaseTypeValueConverter implements ValueConverter<Object> {

    /**
     * 非数组类型，取出数组中的第一个参数
     *
     * @param value
     * @param valueClass
     * @return
     */
    @Override
    public Object converter(
            @NonNull Object value,
            @NonNull Class<Object> valueClass
    ) {
        Assert.isTrue(!valueClass.isArray(), "valueClass 不能是数组类型！");
        String val = value.toString();
        Assert.notNull(val);
        if (valueClass.equals(ClassUtils.INT_CLASS) || valueClass.equals(ClassUtils.INT_WRAP_CLASS)) {
            Object object = Integer.parseInt(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.LONG_CLASS) || valueClass.equals(ClassUtils.LONG_WRAP_CLASS)) {
            Object object = Long.parseLong(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.FLOAT_CLASS) || valueClass.equals(ClassUtils.FLOAT_WRAP_CLASS)) {
            Object object = Float.parseFloat(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.DOUBLE_CLASS) || valueClass.equals(ClassUtils.DOUBLE_WRAP_CLASS)) {
            Object object = Double.parseDouble(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.SHORT_CLASS) || valueClass.equals(ClassUtils.SHORT_WRAP_CLASS)) {
            Object object = Short.parseShort(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.BYTE_CLASS) || valueClass.equals(ClassUtils.BYTE_WRAP_CLASS)) {
            Object object = Byte.parseByte(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.BOOLEAN_CLASS) || valueClass.equals(ClassUtils.BOOLEAN_WRAP_CLASS)) {
            Object object = Boolean.parseBoolean(val);
            return object;
        }
        if (valueClass.equals(ClassUtils.CHAR_CLASS) || valueClass.equals(ClassUtils.CHAR_WRAP_CLASS)) {
            Assert.isTrue(val.length() == 1, "参数长度异常，无法转换char类型！");
            Object object = val.charAt(0);
            return object;
        }
        if (valueClass.equals(ClassUtils.STRING_CLASS)) {
            Object object = val;
            return object;
        }
        throw new UnsupportedOperationException("类型异常，非基本数据类型！");
    }
}
