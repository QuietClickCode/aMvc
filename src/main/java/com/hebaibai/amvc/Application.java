package com.hebaibai.amvc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hebaibai.amvc.namegetter.AsmParamNameGetter;
import com.hebaibai.amvc.objectfactory.AlwaysNewObjectFactory;
import com.hebaibai.amvc.objectfactory.ObjectFactory;
import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.ClassUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * aMac
 *
 * @author hjx
 */
@Log
public class Application {

    private static final String NOT_FIND = "缺少配置！";
    //urlMapping节点名称
    private static final String MAPPING_NODE = "mapping";
    //是否支持注解
    private static final String ANNOTATION_SUPPORT_NODE = "annotationSupport";

    /**
     * 映射的工厂类
     */
    private UrlMethodMappingFactory urlMethodMappingFactory = new UrlMethodMappingFactory();

    /**
     * 生成对象的工厂
     */
    private ObjectFactory objectFactory;

    /**
     * 应用的名称
     */
    private String applicationName;

    /**
     * 应用中的所有urlMapping
     */
    private Map<String, UrlMethodMapping> applicationUrlMapping = new ConcurrentHashMap<>();


    /**
     * 构造函数，通过servletName加载配置
     *
     * @param applicationName
     */
    public Application(String applicationName) {
        this.applicationName = applicationName;
        init();
    }

    /**
     * 初始化配置
     */
    @SneakyThrows(IOException.class)
    protected void init() {
        String configFileName = applicationName + ".json";
        InputStream inputStream = ClassUtils.getClassLoader().getResourceAsStream(configFileName);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String config = new String(bytes, "utf-8");
        //应用配置
        JSONObject configJson = JSONObject.parseObject(config);
        boolean annotationSupport = configJson.getBoolean(ANNOTATION_SUPPORT_NODE);

        //TODO:是否开启注解，注解支持之后写
        Assert.isTrue(!annotationSupport, "现在不支持此功能！");
        urlMethodMappingFactory.setParamNameGetter(new AsmParamNameGetter());

        //TODO:生成对象的工厂类（当先默认为每次都new一个新的对象）
        this.objectFactory = new AlwaysNewObjectFactory();

        JSONArray jsonArray = configJson.getJSONArray(MAPPING_NODE);
        Assert.notNull(jsonArray, MAPPING_NODE + NOT_FIND);
        for (int i = 0; i < jsonArray.size(); i++) {
            UrlMethodMapping mapping = urlMethodMappingFactory.getUrlMethodMappingByJson(jsonArray.getJSONObject(i));
            addApplicationUrlMapping(mapping);
        }
    }

    /**
     * 获取Url的描述
     *
     * @param requestType
     * @param url
     * @return
     */
    protected String getUrlDescribe(RequestType requestType, @NonNull String url) {
        return requestType.name() + ":" + url;
    }

    /**
     * 将映射映射添加进应用
     *
     * @param urlMethodMapping
     */
    protected void addApplicationUrlMapping(@NonNull UrlMethodMapping urlMethodMapping) {
        RequestType[] requestTypes = urlMethodMapping.getRequestTypes();
        String url = urlMethodMapping.getUrl();
        for (RequestType requestType : requestTypes) {
            String urlDescribe = getUrlDescribe(requestType, url);
            if (applicationUrlMapping.containsKey(urlDescribe)) {
                throw new UnsupportedOperationException(urlDescribe + "已经存在！");
            }
            Method method = urlMethodMapping.getMethod();
            Class aClass = urlMethodMapping.getClass();
            log.info("mapping url：" + urlDescribe + " to " + aClass.getName() + "." + method.getName());
            applicationUrlMapping.put(urlDescribe, urlMethodMapping);
        }
    }

    /**
     * 根据url描述获取 UrlMethodMapping
     *
     * @param urlDescribe
     * @return
     */
    protected UrlMethodMapping getUrlMethodMapping(@NonNull String urlDescribe) {
        UrlMethodMapping urlMethodMapping = applicationUrlMapping.get(urlDescribe);
        return urlMethodMapping;
    }

    /**
     * 生成对象的工厂
     *
     * @return
     */
    protected ObjectFactory getObjectFactory() {
        return this.objectFactory;
    }

}
