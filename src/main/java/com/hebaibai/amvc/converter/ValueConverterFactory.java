package com.hebaibai.amvc.converter;

import com.hebaibai.amvc.utils.ClassUtils;

/**
 * 数据转换工厂类
 */
public class ValueConverterFactory {

    private static final ValueConverter BASE_TYPE_VALUE_CONVERTER = new BaseTypeValueConverter();
    private static final ValueConverter BASE_TYPE_ARRAY_VALUE_CONVERTER = new BaseTypeArrayValueConverter();

    /**
     * 根据目标类型获取转换器
     *
     * @param valueClass
     * @return
     */
    public static ValueConverter getValueConverter(Class valueClass) {
        boolean baseClass = ClassUtils.isBaseClass(valueClass);
        if (baseClass) {
            return BASE_TYPE_VALUE_CONVERTER;
        }
        if (valueClass.isArray()) {
            return BASE_TYPE_ARRAY_VALUE_CONVERTER;
        }
        throw new UnsupportedOperationException("数据类型：" + valueClass.getName() + " 不支持转换！");
    }
}
