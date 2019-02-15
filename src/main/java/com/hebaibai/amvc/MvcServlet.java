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
     * 初始化项目
     * 1：获取Servlet名称，加载名称相同的配置文件
     * 2：加载配置文件中的urlMapping
     * 3：加载其他配置
     */
    @Override
    @SneakyThrows(ServletException.class)
    public void init(ServletConfig config) {
        super.init(config);
        String servletName = config.getServletName();
        log.info("【 aMvc " + servletName + " starting 。。。】");
        application = new Application(servletName);
        afterInitMvc(application);
        log.info("【 aMvc " + servletName + " finish 。。。】");
    }


    /**
     * 添加一个空方法，在框架加载完毕后执行。
     * 在配合其他框架使用的时候，继承此类并重写重写此方法，
     * 比如在集成Ioc框架时，修改Application中的objectFactory对象即可。
     *
     * @param application
     */
    protected void afterInitMvc(Application application) {
    }

    /**
     * 执行请求
     *
     * @param request
     * @param response
     */
    @SneakyThrows({IOException.class})
    private void doInvoke(HttpServletRequest request, HttpServletResponse response) {
        //根据请求获取对应的UrlMethodMapping
        UrlMethodMapping urlMethodMapping = application.getUrlMethodMapping(request);
        if (urlMethodMapping == null) {
            unsupportedMethod(request, response);
            return;
        }
        //获取请求中的参数
        MethodValueGetter methodValueGetter = application.getMethodValueGetter();
        Object[] methodValue = methodValueGetter.getMethodValue(urlMethodMapping, request, response);
        //方法执行结果
        Object result = invokeMethod(urlMethodMapping, methodValue);
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
     * @param methodValue
     * @return
     */
    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
    private Object invokeMethod(UrlMethodMapping urlMethodMapping, Object[] methodValue) {
        Method method = urlMethodMapping.getMethod();
        Class objectClass = urlMethodMapping.getObjectClass();
        //通过对象工厂实例化objectClass
        ObjectFactory objectFactory = application.getObjectFactory();
        Object object = objectFactory.getObject(objectClass);
        return method.invoke(object, methodValue);
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
