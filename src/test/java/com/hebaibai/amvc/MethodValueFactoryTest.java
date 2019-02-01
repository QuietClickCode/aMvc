package com.hebaibai.amvc;

import com.hebaibai.amvc.utils.ClassUtils;
import org.junit.Test;

import java.lang.reflect.Method;

public class MethodValueFactoryTest {

    @Test
    public void converterBaseType() {
        MethodValueFactory methodValueFactory = new MethodValueFactory();
        for (Class aClass : ClassUtils.JAVA_CLASS_LIST) {
            try {
                Object value = methodValueFactory.toBaseType("何嘉旋", aClass, "text");
                System.out.println("目标：" + aClass.getName() + " 结果：" + value.getClass() + " 值：" + value);
            } catch (Exception e) {

            }
        }
    }

    @Test
    public void converterBaseType2() {
        MethodValueFactory methodValueFactory = new MethodValueFactory();
        Class aClass = Method.class;
        Object value = methodValueFactory.toBaseType("何嘉旋", aClass, "text");
        System.out.println("目标：" + aClass.getName() + " 结果：" + value.getClass() + " 值：" + value);
    }
}