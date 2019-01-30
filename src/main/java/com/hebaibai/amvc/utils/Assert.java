package com.hebaibai.amvc.utils;


/**
 * 断言
 *
 * @author hjx
 */
public class Assert {

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new RuntimeException(msg);
        }
    }

    public static void notNull(Object obj) {
        if (obj == null) {
            throw new RuntimeException("参数不能为null");
        }
    }

    public static void isTrue(boolean b, String msg) {
        if (!b) {
            throw new RuntimeException(msg);
        }
    }
}
