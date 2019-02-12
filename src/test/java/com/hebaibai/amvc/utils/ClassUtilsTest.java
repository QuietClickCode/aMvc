package com.hebaibai.amvc.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class ClassUtilsTest {

    @Test
    public void forName() throws ClassNotFoundException {
        Class<?> aClass = Class.forName(HttpServletRequest.class.getName());
    }

    @Test
    public void getClassByPackage() {
        Set<Class> classes = new HashSet<>();
        ClassUtils.getClassByPackage("com", classes);
        for (Class aClass : classes) {
            System.out.println(aClass.getName());
        }
    }
}