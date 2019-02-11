package com.hebaibai.amvc;

import com.alibaba.fastjson.JSONObject;
import com.hebaibai.amvc.objectfactory.ObjectFactory;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * mvc的入口
 *
 * @author hjx
 */
@Log
public class MvcServlet extends HttpServlet {

    /**
     * 应用
     */
    private Application application;

    /**
     * 请求中的参数获取器
     */
    private MethodValueGetter methodValueGetter;


    /**
     * 初始化项目
     * 1：获取Servlet名称，加载名称相同的配置文件
     * 2：加载配置文件中的urlMapping
     */
    @Override
    @SneakyThrows(ServletException.class)
    public void init(ServletConfig config) {
        super.init(config);
        String servletName = config.getServletName();
        log.info("aMvc init servletName：" + servletName);
        application = new Application(servletName);
        methodValueGetter = new MethodValueGetter();
    }

    /**
     * 执行请求
     *
     * @param request
     * @param response
     */
    @SneakyThrows({IOException.class})
    private void doInvoke(HttpServletRequest request, HttpServletResponse response) {
        RequestType requestType = getRequestType(request.getMethod());
        String urlDescribe = application.getUrlDescribe(requestType, request.getPathInfo());
        UrlMethodMapping urlMethodMapping = application.getUrlMethodMapping(urlDescribe);
        //没有找到对应的mapping
        if (urlMethodMapping == null) {
            unsupportedMethod(request, response);
            return;
        }
        //方法执行结果
        Object result = invokeMethod(urlMethodMapping, request);
        //TODO:视图处理，先以JSON形式返回
        response.setHeader("content-type", "application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(result));
        writer.close();
    }

    /**
     * 反射执行方法
     *
     * @param urlMethodMapping
     * @param request
     * @return
     */
    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
    private Object invokeMethod(UrlMethodMapping urlMethodMapping, HttpServletRequest request) {
        Object[] methodValue = methodValueGetter.getMethodValue(urlMethodMapping.getParamClasses(), urlMethodMapping.getParamNames(), request);
        Method method = urlMethodMapping.getMethod();
        Class objectClass = urlMethodMapping.getObjectClass();
        //通过对象工厂实例化objectClass
        ObjectFactory objectFactory = application.getObjectFactory();
        Object object = objectFactory.getObject(objectClass);
        return method.invoke(object, methodValue);
    }

    /**
     * 根据http请求方式获取RequestType
     *
     * @param requestMethod
     * @return
     */
    private RequestType getRequestType(String requestMethod) {
        if (requestMethod.equalsIgnoreCase(RequestType.GET.name())) {
            return RequestType.GET;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.POST.name())) {
            return RequestType.POST;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.PUT.name())) {
            return RequestType.PUT;
        }
        if (requestMethod.equalsIgnoreCase(RequestType.DELETE.name())) {
            return RequestType.DELETE;
        }
        throw new UnsupportedOperationException("请求方式不支持：" + requestMethod);
    }

    /**
     * 不支持的请求方式
     *
     * @param request
     * @param response
     */
    @SneakyThrows(IOException.class)
    private void unsupportedMethod(HttpServletRequest request, HttpServletResponse response) {
        String protocol = request.getProtocol();
        String method = request.getMethod();
        String errorMsg = "不支持的请求方式：" + method + "！";
        if (protocol.endsWith("1.1")) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, errorMsg);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorMsg);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doInvoke(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doInvoke(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        doInvoke(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        doInvoke(request, response);
    }
}
