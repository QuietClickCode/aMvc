package com.hebaibai.amvc;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MethodValueGetterTest {

    MethodValueGetter methodValueGetter = new MethodValueGetter();

    @Test
    public void getMethodValue() {

        Map<String, String[]> value = new HashMap<>();
        value.put("name", new String[]{"何嘉旋"});
        value.put("age", new String[]{"20"});
//        value.put("children", new String[]{"何大白1", "何大白2", "何大白3", "何大白4"});

        Object[] methodValue = methodValueGetter.getMethodValue(
                new Class[]{String.class, int.class, String[].class},
                new String[]{"name", "age", "children"},
                value
        );

        for (int i = 0; i < methodValue.length; i++) {
            Object obj = methodValue[i];
            if (obj == null) {
                System.out.println("null");
                continue;
            }
            Class<?> objClass = obj.getClass();
            if (objClass.isArray()) {
                System.out.println(Arrays.toString((Object[]) obj));
            } else {
                System.out.println(obj);
            }
        }
    }
}