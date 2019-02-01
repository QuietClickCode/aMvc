package com.hebaibai.amvc;

import com.hebaibai.amvc.converter.BaseTypeValueConverter;
import com.hebaibai.amvc.utils.ClassUtils;
import org.junit.Test;

public class BaseTypeValueConverterTest {

    BaseTypeValueConverter baseTypeValueConverter = new BaseTypeValueConverter();

    @Test
    public void converter() {

        for (Class aClass : ClassUtils.JAVA_CLASS_LIST) {
            try {
                Object value = baseTypeValueConverter.converter(new String[]{"何嘉旋"}, aClass);
                System.out.println("目标：" + aClass.getName() + " 结果：" + value.getClass() + " 值：" + value);
            } catch (Exception e) {

            }
        }
    }

    @Test
    public void converterToInt() {
        int[] ints = baseTypeValueConverter.converter(new String[]{"1123"}, int[].class);
        System.out.println(ints);
    }

    @Test
    public void converterToBoolean() {
        boolean value = baseTypeValueConverter.converter(new String[]{"true"}, boolean.class);
        System.out.println(value);
    }
}