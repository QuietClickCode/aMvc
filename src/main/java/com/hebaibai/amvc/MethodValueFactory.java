package com.hebaibai.amvc;

import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.ClassUtils;

import java.util.Map;

/**
 * 用于获取方法入参的值
 *
 * @author hjx
 */
public class MethodValueFactory {

    /**
     * 获取方法的入参
     *
     * @param valueTypes
     * @param valueNames
     * @param valueSource
     * @return
     */
    public Object[] getMethodValue(Class[] valueTypes, String[] valueNames, Map<String, String> valueSource) {
        Assert.notNull(valueTypes);
        Assert.notNull(valueNames);
        Assert.notNull(valueSource);
        Assert.isTrue(valueNames.length == valueTypes.length,
                "getMethodValue() 参数长度不一致！");
        int length = valueNames.length;
        Object[] values = new Object[length];
        for (int i = 0; i < values.length; i++) {
            Class valueType = valueTypes[i];
            String valueName = valueNames[i];
            String strValue = valueSource.get(valueName);
            //来源参数中 key不存在或者key的值不存在，设置值为null
            if (strValue == null) {
                values[i] = null;
                continue;
            }
            //判断是否是基本数据类型
            if (ClassUtils.isBaseClass(valueType)) {
            }

        }
        return values;
    }

    /**
     * 将String类型转换位基本数据类型
     *
     * @param value
     * @param valueType
     * @param valueName
     * @return
     */
    public Object toBaseType(String value, Class valueType, String valueName) {
        if (valueType.equals(ClassUtils.INT_CLASS) || valueType.equals(ClassUtils.INT_WRAP_CLASS)) {
            return Integer.parseInt(value);
        }
        if (valueType.equals(ClassUtils.LONG_CLASS) || valueType.equals(ClassUtils.LONG_WRAP_CLASS)) {
            return Long.parseLong(value);
        }
        if (valueType.equals(ClassUtils.FLOAT_CLASS) || valueType.equals(ClassUtils.FLOAT_WRAP_CLASS)) {
            return Float.parseFloat(value);
        }
        if (valueType.equals(ClassUtils.DOUBLE_CLASS) || valueType.equals(ClassUtils.DOUBLE_WRAP_CLASS)) {
            return Double.parseDouble(value);
        }
        if (valueType.equals(ClassUtils.SHORT_CLASS) || valueType.equals(ClassUtils.SHORT_WRAP_CLASS)) {
            return Short.parseShort(value);
        }
        if (valueType.equals(ClassUtils.BYTE_CLASS) || valueType.equals(ClassUtils.BYTE_WRAP_CLASS)) {
            return Byte.parseByte(value);
        }
        if (valueType.equals(ClassUtils.BOOLEAN_CLASS) || valueType.equals(ClassUtils.BOOLEAN_WRAP_CLASS)) {
            return Boolean.parseBoolean(value);
        }
        if (valueType.equals(ClassUtils.CHAR_CLASS) || valueType.equals(ClassUtils.CHAR_WRAP_CLASS)) {
            Assert.isTrue(value.length() == 1, "参数：" + valueName + " 异常！");
            return value.charAt(0);
        }
        if (valueType.equals(ClassUtils.STRING_CLASS)) {
            return value;
        }
        throw new UnsupportedOperationException("valueName：" + valueName + "，valueType: " + valueType + " 类型异常，非基本数据类型！");
    }

}
