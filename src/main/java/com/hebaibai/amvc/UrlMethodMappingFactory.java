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
    private static final String REQUEST_TYPE = "requestType";
    private static final String METHOD = "method";
    private static final String CLASS = "objectClass";
    private static final String PARAM_TYPES = "paramTypes";
    private static final String NOT_FIND = "缺少配置！";

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
        String className = jsonObject.getString(CLASS);
        //请求类型
        JSONArray requestType = jsonObject.getJSONArray(REQUEST_TYPE);
        Assert.notNull(requestType, REQUEST_TYPE + "节点不存在！");
        Assert.isTrue(requestType.size() > 0, REQUEST_TYPE + "节点缺少配置！");
        String[] types = jsonArrayToArray(requestType);
        //数据类型
        JSONArray jsonParamTypes = jsonObject.getJSONArray(PARAM_TYPES);
        String[] paramTypes = jsonArrayToArray(jsonParamTypes);
        return getUrlMethodMapping(url, types, className, method, paramTypes);
    }

    /**
     * @param url             请求地址
     * @param requestTypes    请求方式
     * @param className       实例对象
     * @param methodName      url对应的方法
     * @param paramClassNames 请求参数类型
     * @return
     */
    private UrlMethodMapping getUrlMethodMapping(
            String url, String[] requestTypes, String className, String methodName, String[] paramClassNames
    ) {
        //class
        Class objectClass = ClassUtils.forName(className);
        //请求方式
        RequestType[] types = getRequestTypes(requestTypes);
        //参数类型
        Class[] paramCLasses = getParamCLasses(paramClassNames);
        //获取方法
        Method method = ClassUtils.getMethod(objectClass, methodName, paramCLasses);
        return getUrlMethodMapping(url, types, objectClass, method, paramCLasses);
    }

    /**
     * @param url          请求地址
     * @param requestTypes http请求方式
     * @param objectClass  实例对象的Class
     * @param method       url对应的方法
     * @param paramClasses 请求参数类型
     * @return
     */
    public UrlMethodMapping getUrlMethodMapping(
            String url, RequestType[] requestTypes, Class objectClass, Method method, Class[] paramClasses
    ) {
        Assert.notNull(url, URL + NOT_FIND);
        Assert.notNull(requestTypes, REQUEST_TYPE + NOT_FIND);
        Assert.isTrue(requestTypes.length > 0, REQUEST_TYPE + NOT_FIND);
        Assert.notNull(objectClass, CLASS + NOT_FIND);
        Assert.notNull(method, METHOD + NOT_FIND);
        Assert.notNull(paramClasses, PARAM_TYPES + NOT_FIND);

        //class实例化对象
        Object object = objectFactory.getObject(objectClass);
        Assert.notNull(object, "objectFactory.getObject() 获取失败！objectClass：" + objectClass.getName());
        //获取参数名称
        String[] paramNames = paramNameGetter.getParamNames(method);
        Assert.notNull(paramNames, "paramNameGetter.getParamNames() 执行失败！method：" + method.getName());
        Assert.isTrue(paramNames.length == paramClasses.length, "方法名称取出异常 method：" + method.getName());
        //组装参数
        UrlMethodMapping mapping = new UrlMethodMapping();
        mapping.setMethod(method);
        mapping.setUrl(url);
        mapping.setRequestTypes(requestTypes);
        mapping.setObject(object);
        mapping.setParamClasses(paramClasses);
        mapping.setObjectClass(objectClass);
        mapping.setParamNames(paramNames);
        return mapping;
    }

    /**
     * 从配置文件中获取的字符串类型转换位枚举类型
     *
     * @param requestTypes
     * @return
     */
    RequestType[] getRequestTypes(String[] requestTypes) {
        RequestType[] types = new RequestType[requestTypes.length];
        for (int i = 0; i < requestTypes.length; i++) {
            if (RequestType.DELETE.name().equalsIgnoreCase(requestTypes[i])) {
                types[i] = RequestType.DELETE;
            } else if (RequestType.PUT.name().equalsIgnoreCase(requestTypes[i])) {
                types[i] = RequestType.PUT;
            } else if (RequestType.POST.name().equalsIgnoreCase(requestTypes[i])) {
                types[i] = RequestType.POST;
            } else if (RequestType.GET.name().equalsIgnoreCase(requestTypes[i])) {
                types[i] = RequestType.GET;
            } else {
                throw new UnsupportedOperationException("不支持的请求格式：'" + requestTypes[i] + "'");
            }
        }
        return types;
    }

    /**
     * 将className的数组转转换为Class对象数组
     *
     * @param paramTypes
     * @return
     */
    Class[] getParamCLasses(String[] paramTypes) {
        Class[] paramclasses = new Class[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            String paramType = paramTypes[i];
            Class paramClass = ClassUtils.getBaseClassByName(paramType);
            if (paramClass == null) {
                paramClass = ClassUtils.forName(paramType);
            }
            Assert.notNull(paramClass, "参数类型：" + paramType + " 加载失败！");
            paramclasses[i] = paramClass;
        }
        return paramclasses;
    }

    /**
     * json数组转换数组
     *
     * @param jsonArray
     * @return
     */
    String[] jsonArrayToArray(JSONArray jsonArray) {
        Assert.notNull(jsonArray);
        String[] array = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            array[i] = jsonArray.getString(i);
        }
        return array;
    }
}
