package com.hebaibai.amvc;

import com.hebaibai.amvc.utils.Assert;

/**
 * 每次都新产生一个实例
 */
public class AlwaysNewObjectFactory implements ObjectFactory {

    @Override
    public Object getObject(Class objectClass) {
        Assert.notNull(objectClass);
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
