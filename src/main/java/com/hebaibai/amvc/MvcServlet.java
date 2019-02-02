package com.hebaibai.amvc;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
    protected Application application;

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

}
