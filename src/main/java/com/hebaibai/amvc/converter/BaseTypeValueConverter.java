package com.hebaibai.amvc.converter;

import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.ClassUtils;

/**
 * 基本数据类型的转换
 *
 * @author hjx
 */
public class BaseTypeValueConverter implements ValueConverter {

    /**
     * 非数组类型，取出数组中的第一个参数
     *
     * @param value
     * @param valueClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T converter(String[] value, Class<T> valueClass) {
        Assert.notNull(value);
        Assert.isTrue(!valueClass.isArray(), "valueClass 不能是数组类型！");
        String val = value[0];
        Assert.notNull(val);
        Object object = null;
        if (valueClass.equals(ClassUtils.INT_CLASS) || valueClass.equals(ClassUtils.INT_WRAP_CLASS)) {
            object = Integer.parseInt(val);
        } else if (valueClass.equals(ClassUtils.LONG_CLASS) || valueClass.equals(ClassUtils.LONG_WRAP_CLASS)) {
            object = Long.parseLong(val);
        } else if (valueClass.equals(ClassUtils.FLOAT_CLASS) || valueClass.equals(ClassUtils.FLOAT_WRAP_CLASS)) {
            object = Float.parseFloat(val);
        } else if (valueClass.equals(ClassUtils.DOUBLE_CLASS) || valueClass.equals(ClassUtils.DOUBLE_WRAP_CLASS)) {
            object = Double.parseDouble(val);
        } else if (valueClass.equals(ClassUtils.SHORT_CLASS) || valueClass.equals(ClassUtils.SHORT_WRAP_CLASS)) {
            object = Short.parseShort(val);
        } else if (valueClass.equals(ClassUtils.BYTE_CLASS) || valueClass.equals(ClassUtils.BYTE_WRAP_CLASS)) {
            object = Byte.parseByte(val);
        } else if (valueClass.equals(ClassUtils.BOOLEAN_CLASS) || valueClass.equals(ClassUtils.BOOLEAN_WRAP_CLASS)) {
            object = Boolean.parseBoolean(val);
        } else if (valueClass.equals(ClassUtils.CHAR_CLASS) || valueClass.equals(ClassUtils.CHAR_WRAP_CLASS)) {
            Assert.isTrue(val.length() == 1, "参数长度异常，无法转换char类型！");
            object = val.charAt(0);
        } else if (valueClass.equals(ClassUtils.STRING_CLASS)) {
            object = val;
        } else {
            throw new UnsupportedOperationException("类型异常，非基本数据类型！");
        }
        return (T) object;
    }
}
