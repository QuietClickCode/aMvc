package com.hebaibai.amvc.utils;

import org.objectweb.asm.*;

import java.util.List;

/**
 * asn class访问器
 * 用于提取方法的实际参数名称
 *
 * @author hjx
 */
public class MethodParamNameClassVisitor extends ClassVisitor {

    /**
     * 方法的参数名称
     */
    private List<String> paramNames;

    /**
     * 方法的名称
     */
    private String methodName;

    /**
     * 方法的参数类型
     */
    private Class[] patamTypes;

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor visitMethod = super.visitMethod(access, name, descriptor, signature, exceptions);
        boolean sameMethod = sameMethod(name, methodName, descriptor, patamTypes);
        //如果是相同的方法, 执行取参数名称的操作
        if (sameMethod) {
            MethodParamNameMethodVisitor paramNameMethodVisitor = new MethodParamNameMethodVisitor(Opcodes.ASM4, visitMethod);
            paramNameMethodVisitor.paramNames = this.paramNames;
            paramNameMethodVisitor.paramLength = this.patamTypes.length;
            return paramNameMethodVisitor;
        }
        return visitMethod;
    }

    /**
     * 是否是相同的方法
     *
     * @param methodName
     * @param methodName2
     * @param descriptor
     * @param paramTypes
     * @return
     */
    private boolean sameMethod(String methodName, String methodName2, String descriptor, Class[] paramTypes) {
        //方法名相同
        Assert.notNull(methodName);
        Assert.notNull(methodName2);
        if (methodName.equals(methodName2)) {
            Type[] argumentTypes = Type.getArgumentTypes(descriptor);
            //参数长度相同
            if (argumentTypes.length == paramTypes.length) {
                //参数类型相同
                for (int i = 0; i < argumentTypes.length; i++) {
                    if (!Type.getType(paramTypes[i]).equals(argumentTypes[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @param paramNames 取出的参数名称，传入一个空的集合
     * @param methodName 目标方法名称
     * @param patamTypes 目标方法的参数类型
     */
    public MethodParamNameClassVisitor(List<String> paramNames, String methodName, Class[] patamTypes) {
        super(Opcodes.ASM4);
        this.paramNames = paramNames;
        this.methodName = methodName;
        this.patamTypes = patamTypes;
    }


    /**
     * 禁止的操作
     * 无法正确使用,抛出异常
     *
     * @param api
     */
    public MethodParamNameClassVisitor(int api) {
        super(api);
        throw new RuntimeException("不支持的操作, 请使用构造函数:MethodParamNameClassVisitor(List<String> paramNames, int patamLength) !");
    }

}

/**
 * 用于取出方法的参数实际名称
 */
class MethodParamNameMethodVisitor extends MethodVisitor {

    /**
     * 方法的参数名称
     */
    List<String> paramNames;

    /**
     * 方法的参数长度
     */
    int paramLength;

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, descriptor, signature, start, end, index);
        //index 为0 时, name是this
        //根据方法实际参数长度截取参数名称
        if (index != 0 && paramNames.size() < paramLength) {
            paramNames.add(name);
        }
    }

    public MethodParamNameMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }
}
