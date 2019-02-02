package com.hebaibai.amvc.objectfactory;

/**
 * 创建Object的工厂
 * 需要自己实现
 *
 * @author hjx
 */
public interface ObjectFactory {

    /**
     * 根据className 获取 实例化对象
     *
     * @param objectClass
     * @return
     */
    Object getObject(Class objectClass);
}
