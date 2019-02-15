package com.hebaibai.amvc.utils;

import org.junit.Test;

public class UrlUtilsTest {

    @Test
    public void makeUrl() {
        String url = UrlUtils.distinctUrlSlash("");
        System.out.println(url);
    }


    @Test
    public void matchesUrl() {
        System.out.println(UrlUtils.matchesUrl("GET:/user/{name}", "GET:/user/{age}"));
    }
}