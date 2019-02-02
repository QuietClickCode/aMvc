package com.hebaibai.amvc.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

public class ClassUtilsTest {

    @Test
    public void name() throws ClassNotFoundException {
        Class<?> aClass = Class.forName(HttpServletRequest.class.getName());

    }
}