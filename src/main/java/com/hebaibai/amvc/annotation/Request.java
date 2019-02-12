package com.hebaibai.amvc.annotation;

import com.hebaibai.amvc.RequestType;

import java.lang.annotation.*;

/**
 * 添加在class上：
 * 表示这个类中的，添加了@Request注解的method被映射为一个http地址。
 * <p>
 * 添加在method：
 * 表示这个method被映射为一个http地址。
 *
 * @author hjx
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {

    /**
     * 请求类型
     * 支持GET，POST，DELETE，PUT
     *
     * @return
     */
    RequestType[] type() default {RequestType.GET, RequestType.POST, RequestType.DELETE, RequestType.PUT};

    /**
     * 请求地址
     * 添加在class上时，会将value中的值添加在其他方法上的@Request.value()的值前，作为基础地址。
     *
     * @return
     */
    String value() default "/";
}
