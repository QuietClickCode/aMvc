package com.hebaibai.amvc.objectfactory;

import lombok.NonNull;

/**
 * 每次都新产生一个实例
 *
 * @author hjx
 */
public class AlwaysNewObjectFactory implements ObjectFactory {

    @Override
    public Object getObject(@NonNull Class objectClass) {
        try {
            return objectClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("实例化对象失败：" + objectClass.getName());
    }
}
