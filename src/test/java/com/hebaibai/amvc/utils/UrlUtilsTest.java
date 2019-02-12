package com.hebaibai.amvc.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UrlUtilsTest {

    @Test
    public void makeUrl() {
        String url = UrlUtils.makeUrl("");
        System.out.println(url);
    }
}