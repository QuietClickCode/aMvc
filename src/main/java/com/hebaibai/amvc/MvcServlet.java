package com.hebaibai.amvc;

import com.alibaba.fastjson.JSONObject;
import com.hebaibai.amvc.objectfactory.AlwaysNewObjectFactory;
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
    Application application;

    /**
     * 请求中的参数获取器
     */
    MethodValueGetter methodValueGetter = new MethodValueGetter();


    /**
     * 初始化项目
     * 1：获取Servlet名称，加载名称相同的配置文件
     * 2：加载配置文件中的urlMapping
     *
     * @throws ServletException
     */
    @Override
    @SneakyThrows(ServletException.class)
    public void init(ServletConfig config) {
        super.init(config);
        String servletName = config.getServletName();
        log.info("aMvc init servletName：" + servletName);
        application = new Application(servletName);
    }

    /**
     * 执行请求
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    void doInvoke(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestType requestType = getRequestType(req.getMethod());
        String urlDescribe = application.getUrlDescribe(requestType, req.getPathInfo());
        UrlMethodMapping urlMethodMapping = application.getUrlMethodMapping(urlDescribe);
        if (urlMethodMapping == null) {
            if (requestType == RequestType.GET) {
                super.doGet(req, resp);
                return;
            }
            if (requestType == RequestType.POST) {
                super.doPost(req, resp);
                return;
            }
            if (requestType == RequestType.PUT) {
                super.doPut(req, resp);
                return;
            }
            if (requestType == RequestType.DELETE) {
                super.doDelete(req, resp);
                return;
            }
        }
        //方法执行结果
        Object result = invokeMethod(urlMethodMapping, req);
        PrintWriter writer = resp.getWriter();
        writer.write(JSONObject.toJSONString(result));
        writer.close();
    }

    /**
     * 反射执行方法
     *
     * @param urlMethodMapping
     * @param req
     * @return
     */
    @SneakyThrows({IllegalAccessException.class, InvocationTargetException.class})
    Object invokeMethod(UrlMethodMapping urlMethodMapping, HttpServletRequest req) {
        Object[] methodValue = methodValueGetter.getMethodValue(urlMethodMapping.getParamClasses(), urlMethodMapping.getParamNames(), req);
        Method method = urlMethodMapping.getMethod();
        Class objectClass = urlMethodMapping.getObjectClass();
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
    RequestType getRequestType(String requestMethod) {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doInvoke(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doInvoke(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doInvoke(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doInvoke(req, resp);
    }
}
