package com.hebaibai.amvc;

import com.hebaibai.amvc.converter.BaseTypeValueConverter;
import org.junit.Test;

public class BaseTypeValueConverterTest {

    BaseTypeValueConverter baseTypeValueConverter = new BaseTypeValueConverter();

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