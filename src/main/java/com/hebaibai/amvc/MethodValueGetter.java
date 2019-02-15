package com.hebaibai.amvc;

import com.hebaibai.amvc.converter.ValueConverter;
import com.hebaibai.amvc.converter.ValueConverterFactory;
import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.UrlUtils;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用于获取方法入参的值
 *
 * @author hjx
 */
public class MethodValueGetter {

    /**
     * 获取方法的入参
     *
     * @param urlMethodMapping
     * @param request
     * @param response
     * @return
     */
    public Object[] getMethodValue(
            @NonNull UrlMethodMapping urlMethodMapping,
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) {
        //将路径中的参数添加进request中
        String[] valueNames = urlMethodMapping.getParamNames();
        Class[] valueTypes = urlMethodMapping.getParamClasses();
        String mappingUrl = urlMethodMapping.getUrl();
        String url = request.getPathInfo();
        Assert.isTrue(valueNames.length == valueTypes.length, "getMethodValue() 参数长度不一致！");
        //原始请求中的参数
        Map<String, String[]> requestValues = UrlUtils.getRequestValues(request);
        //请求路径中的参数
        Map<String, String> pathParams = UrlUtils.getPathParams(mappingUrl, url);
        //将路径中的参数混入请求中的参数
        for (Map.Entry<String, String> entry : pathParams.entrySet()) {
            requestValues.put(entry.getKey(), new String[]{entry.getValue()});
        }
        int length = valueNames.length;
        Object[] values = new Object[length];
        for (int i = 0; i < valueNames.length; i++) {
            Class valueType = valueTypes[i];
            if (valueType == HttpServletRequest.class) {
                values[i] = request;
                continue;
            }
            if (valueType == HttpServletResponse.class) {
                values[i] = response;
                continue;
            }
            String valueName = valueNames[i];
            String[] strValues = requestValues.get(valueName);
            Assert.notNull(strValues, "参数：" + valueName + " 不存在！");
            ValueConverter valueConverter = ValueConverterFactory.getValueConverter(valueType);
            Object converter = null;
            if (strValues.length == 1) {
                converter = valueConverter.converter(strValues[0], valueType);
            } else {
                converter = valueConverter.converter(strValues, valueType);
            }
            values[i] = converter;
        }
        return values;
    }


}
