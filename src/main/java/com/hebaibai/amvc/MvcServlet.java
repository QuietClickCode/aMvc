package com.hebaibai.amvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hjx
 */
public class MvcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("==================");
        String[] values = req.getParameterValues("name");
        for (String value : values) {
            System.out.println(value);
        }
        System.out.println("==================");
    }
}
