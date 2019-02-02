package com.hebaibai.amvc.namegetter;

import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.MethodParamNameClassVisitor;
import lombok.NonNull;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过asm获取方法参数名称
 */
public class AsmParamNameGetter implements ParamNameGetter {

    /**
     * 通过asm获取method的入参名称
     *
     * @param method
     * @return
     */
    @Override
    public String[] getParamNames(@NonNull Method method) {
        Assert.notNull(method);
        Class aClass = method.getDeclaringClass();
        Parameter[] parameters = method.getParameters();
        String methodName = method.getName();
        String className = aClass.getName();
        ClassReader classReader = null;
        try {
            classReader = new ClassReader(className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class[] paramClasses = new Class[parameters.length];
        for (int i = 0; i < paramClasses.length; i++) {
            paramClasses[i] = parameters[i].getType();
        }
        //暂存参数名称
        List<String> paramNameList = new ArrayList<>();
        MethodParamNameClassVisitor myClassVisitor = new MethodParamNameClassVisitor(
                paramNameList, methodName, paramClasses
        );
        classReader.accept(myClassVisitor, 0);
        return paramNameList.toArray(new String[]{});
    }
}
