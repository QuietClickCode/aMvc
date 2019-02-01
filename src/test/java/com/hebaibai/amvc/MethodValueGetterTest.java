package com.hebaibai.amvc;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MethodValueGetterTest {


    @Test
    public void name() {
        Class aClass = String.class;
        System.out.println(aClass.getComponentType());
    }

    @Test
    public void getMethodValue() {
        MethodValueGetter methodValueGetter = new MethodValueGetter();
        //拼装测试数据
        Map<String, String[]> value = new HashMap<>();
        value.put("name", new String[]{"何白白"});
        value.put("age", new String[]{"20"});
        value.put("children", new String[]{"何大白1", "何大白2", "何大白3", "何大白4"});
        //执行方法
        Object[] methodValue = methodValueGetter.getMethodValue(
                new Class[]{String.class, int.class, String[].class},//入参中的参数类型
                new String[]{"name", "age", "children"},//入参的参数名称
                value//请求中的参数
        );
        //打印结果
        for (int i = 0; i < methodValue.length; i++) {
            Object obj = methodValue[i];
            if (obj == null) {
                System.out.println("null");
                continue;
            }
            Class<?> objClass = obj.getClass();
            if (objClass.isArray()) {
                Object[] objects = (Object[]) obj;
                for (Object object : objects) {
                    System.out.println(object + "===" + object.getClass());
                }
            } else {
                System.out.println(obj + "===" + obj.getClass());
            }
        }
    }
}