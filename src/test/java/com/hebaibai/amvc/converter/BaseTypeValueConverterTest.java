package com.hebaibai.amvc.converter;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaseTypeValueConverterTest {

    @Test
    public void converter() {
        Class integerClass = int.class;
        Object result = new BaseTypeValueConverter().converter("1", integerClass);
        System.out.println(result + "=====" + result.getClass());
    }
}