package com.hebaibai.amvc.namegetter;

import com.hebaibai.amvc.utils.Assert;
import com.hebaibai.amvc.utils.MethodParamNameClassVisitor;
import lombok.NonNull;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过asm获取方法参数名称
 *
 * @author hjx
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
        ClassReader classReader = getClassReader(aClass);
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

    /**
     * 获取 ClassReader
     * 为什么要这么写呢？因为这个项目最终是一个jar文件，
     * 在这个文件中是无法加载依赖这个jar的项目中的class的，
     * 为什么呢？因为类加载器不一样。
     *
     * @param aClass
     * @return
     */
    ClassReader getClassReader(Class aClass) {
        Assert.notNull(aClass);
        String className = aClass.getName();
        String path = getClass().getClassLoader().getResource("/").getPath();
        File classFile = new File(path + className.replace('.', '/') + ".class");
        try (InputStream inputStream = new FileInputStream(classFile)) {
            ClassReader classReader = new ClassReader(inputStream);
            return classReader;
        } catch (IOException e) {
        }
        throw new RuntimeException(className + "无法加载！");
    }
}
