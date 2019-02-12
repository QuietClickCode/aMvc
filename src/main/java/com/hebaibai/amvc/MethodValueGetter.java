package com.hebaibai.amvc;

import com.hebaibai.amvc.converter.ValueConverter;
import com.hebaibai.amvc.converter.ValueConverterFactory;
import com.hebaibai.amvc.utils.Assert;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取方法入参的值
 *
 * @author hjx
 */
public class MethodValueGetter {


    /**
     * 从请求中获取Method的参数
     *
     * @param valueTypes
     * @param valueNames
     * @param httpServletRequest
     * @return
     */
    public Object[] getMethodValue(@NonNull Class[] valueTypes, @NonNull String[] valueNames, @NonNull HttpServletRequest httpServletRequest) {
        Enumeration parameterNames = httpServletRequest.getParameterNames();
        Map<String, String[]> httpValue = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String attrName = parameterNames.nextElement().toString();
            httpValue.put(attrName, httpServletRequest.getParameterValues(attrName));
        }
        return getMethodValue(valueTypes, valueNames, httpValue);
    }

    /**
     * 获取方法的入参
     *
     * @param valueTypes
     * @param valueNames
     * @param valueSource
     * @return
     */
    public Object[] getMethodValue(
            @NonNull Class[] valueTypes,
            @NonNull String[] valueNames,
            @NonNull Map<String, String[]> valueSource
    ) {
        Assert.isTrue(valueNames.length == valueTypes.length, "getMethodValue() 参数长度不一致！");
        int length = valueNames.length;

        Object[] values = new Object[length];
        for (int i = 0; i < valueNames.length; i++) {
            Class valueType = valueTypes[i];
            String valueName = valueNames[i];
            String[] strValues = valueSource.get(valueName);
            Assert.notNull(strValues, "参数：" + valueName + " 不存在！");
            ValueConverter valueConverter = ValueConverterFactory.getValueConverter(valueType);
            Object converter = valueConverter.converter(strValues, valueType);
            values[i] = converter;
        }
        return values;
    }


}
