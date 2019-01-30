package com.hebaibai.amvc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.ClassUtils;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * UrlMethodMapping的工厂类
 *
 * @author hjx
 */
@Getter
@Setter
public class UrlMethodMappingFactory {

    private static final String URL = "url";
    private static final String METHOD = "method";
    private static final String CLASS = "objectClass";
    private static final String PARAM_TYPES = "paramTypes";

    /**
     * 用于根据class实例化对象.
     */
    private ObjectFactory objectFactory;

    /**
     * 用于获取方法的参数名称
     */
    private ParamNameGetter paramNameGetter;

    /**
     * 通过json 获取映射
     *
     * @param json
     * @return
     */
    public UrlMethodMapping getUrlMethodMappingByJson(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString(URL);
        String method = jsonObject.getString(METHOD);
        String objectClass = jsonObject.getString(CLASS);
        JSONArray jsonParamTypes = jsonObject.getJSONArray(PARAM_TYPES);
        String[] paramTypes = new String[jsonParamTypes.size()];

        for (int i = 0; i < jsonParamTypes.size(); i++) {
            paramTypes[i] = jsonParamTypes.getString(i);
        }
        return getUrlMethodMapping(objectClass, method, url, paramTypes);
    }

    private UrlMethodMapping getUrlMethodMapping(String objectClass, String method, String url, String[] paramTypes) {
        Class aClass = ClassUtils.forName(objectClass);
        Assert.notNull(aClass, objectClass + "不存在！");
        int length = paramTypes.length;
        //组装参数
        Class[] paramclasses = new Class[length];
        for (int i = 0; i < length; i++) {
            String paramType = paramTypes[i];
            Class paramClass = ClassUtils.getBaseClassByName(paramType);
            if (paramClass == null) {
                paramClass = ClassUtils.forName(paramType);
            }
            Assert.notNull(paramClass, "参数类型：" + paramType + " 加载失败！");
            paramclasses[i] = paramClass;
        }
        //获取方法
        Method aMethod = ClassUtils.getMethod(aClass, method, paramclasses);
        Assert.notNull(
                aMethod,
                "方法：" + objectClass + "." + method + "(" + String.join("，", paramTypes) + ") 不存在！"
        );
        //获取object
        Object object = objectFactory.getObject(aClass);
        Assert.notNull(object, "objectFactory.getObject(" + objectClass + ") 获取失败！");
        //获取参数名称
        String[] paramNames = paramNameGetter.getParamNames(aMethod);
        Assert.notNull(paramNames, "paramNameGetter.getParamNames(" + method + ") 执行失败！");
        Assert.isTrue(paramNames.length == length, "方法名称取出异常 methodName：" + objectClass);
        UrlMethodMapping mapping = new UrlMethodMapping();
        mapping.setMethod(aMethod);
        mapping.setUrl(url);
        mapping.setObject(object);
        mapping.setParamClasses(paramclasses);
        mapping.setObjectClass(aClass);
        mapping.setParamNames(paramNames);
        return mapping;
    }

}
