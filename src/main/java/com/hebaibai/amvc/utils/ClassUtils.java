package com.hebaibai.amvc.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import sun.net.www.protocol.jar.URLJarFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassUtils
 *
 * @author hjx
 */
@UtilityClass
public class ClassUtils {

    //文件类型 file
    private static final String TYPE_FILE = "file";
    //文件类型 jar
    private static final String TYPE_JAR = "jar";
    //字符集
    private static final String CHARSET_UTF_8 = "UTF-8";
    //class文件标志
    private static final String CLASS_MARK = ".class";
    private static final String DOT = ".";
    private static final String SLASH = "/";


    /**
     * java 基本类型
     */
    public static List<Class> JAVA_BASE_TYPE_LIST = new ArrayList<>();

    public final static Class INT_CLASS = int.class;
    public final static Class LONG_CLASS = long.class;
    public final static Class FLOAT_CLASS = float.class;
    public final static Class DOUBLE_CLASS = double.class;
    public final static Class SHORT_CLASS = short.class;
    public final static Class BYTE_CLASS = byte.class;
    public final static Class BOOLEAN_CLASS = boolean.class;
    public final static Class CHAR_CLASS = char.class;
    public final static Class STRING_CLASS = String.class;
    public final static Class INT_WRAP_CLASS = Integer.class;
    public final static Class LONG_WRAP_CLASS = Long.class;
    public final static Class FLOAT_WRAP_CLASS = Float.class;
    public final static Class DOUBLE_WRAP_CLASS = Double.class;
    public final static Class SHORT_WRAP_CLASS = Short.class;
    public final static Class BOOLEAN_WRAP_CLASS = Boolean.class;
    public final static Class BYTE_WRAP_CLASS = Byte.class;
    public final static Class CHAR_WRAP_CLASS = Character.class;

    static {
        //基本数据类型
        JAVA_BASE_TYPE_LIST.add(INT_CLASS);
        JAVA_BASE_TYPE_LIST.add(LONG_CLASS);
        JAVA_BASE_TYPE_LIST.add(FLOAT_CLASS);
        JAVA_BASE_TYPE_LIST.add(DOUBLE_CLASS);
        JAVA_BASE_TYPE_LIST.add(SHORT_CLASS);
        JAVA_BASE_TYPE_LIST.add(BYTE_CLASS);
        JAVA_BASE_TYPE_LIST.add(BOOLEAN_CLASS);
        JAVA_BASE_TYPE_LIST.add(CHAR_CLASS);
        //基本数据类型（对象）
        JAVA_BASE_TYPE_LIST.add(STRING_CLASS);
        JAVA_BASE_TYPE_LIST.add(INT_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(LONG_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(FLOAT_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(DOUBLE_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(SHORT_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(BOOLEAN_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(BYTE_WRAP_CLASS);
        JAVA_BASE_TYPE_LIST.add(CHAR_WRAP_CLASS);
    }

    /**
     * 检查是否是基本数据类型（包括基本数据类型的包装类）
     *
     * @param aClass
     * @return
     */
    public static boolean isBaseClass(@NonNull Class aClass) {
        int indexOf = JAVA_BASE_TYPE_LIST.indexOf(aClass);
        return indexOf != -1;
    }

    /**
     * 找到基本的数据类型Class
     *
     * @param className
     * @return
     */
    public static Class getBaseClassByName(@NonNull String className) {
        for (Class aClass : JAVA_BASE_TYPE_LIST) {
            if (aClass.getName().equals(className)) {
                return aClass;
            }
        }
        return null;
    }

    /**
     * 获取class
     *
     * @param className
     * @return
     */
    public static Class forName(@NonNull String className) {
        try {
            Class<?> aClass = Class.forName(className);
            return aClass;
        } catch (NoClassDefFoundError e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    /**
     * 获取class中的一个方法
     *
     * @param aClass
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(@NonNull Class aClass, @NonNull String methodName, @NonNull Class[] parameterTypes) {
        try {
            Method method = aClass.getMethod(methodName, parameterTypes);
            return method;
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    /**
     * 获取当前的类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            contextClassLoader = ClassUtils.class.getClassLoader();
        }
        return contextClassLoader;
    }


    /**
     * 从给定的报名中找出所有的class
     *
     * @param packageName
     * @param classes
     */
    @SneakyThrows({IOException.class})
    public static void getClassByPackage(String packageName, Set<Class> classes) {
        Assert.notNull(classes);
        String packagePath = packageName.replace(DOT, SLASH);
        Enumeration<URL> resources = ClassUtils.getClassLoader().getResources(packagePath);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            //文件类型
            String protocol = url.getProtocol();
            String filePath = URLDecoder.decode(url.getFile(), CHARSET_UTF_8);
            if (TYPE_FILE.equals(protocol)) {
                getClassByFilePath(packageName, filePath, classes);
            }
            if (TYPE_JAR.equals(protocol)) {
                //截取文件的路径
                filePath = filePath.substring(filePath.indexOf(":") + 1, filePath.indexOf("!"));
                getClassByJarPath(packageName, filePath, classes);
            }
        }
    }

    /**
     * 在文件夹中递归找出该文件夹中在package中的class
     *
     * @param packageName
     * @param filePath
     * @param classes
     */
    static void getClassByFilePath(String packageName, String filePath, Set<Class> classes) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            return;
        }
        if (targetFile.isDirectory()) {
            File[] files = targetFile.listFiles();
            for (File file : files) {
                String path = file.getPath();
                getClassByFilePath(packageName, path, classes);
            }
        } else {
            //如果是一个class文件
            boolean trueClass = filePath.endsWith(CLASS_MARK);
            if (trueClass) {
                //提取完整的类名
                filePath = filePath.replace(SLASH, DOT);
                int i = filePath.indexOf(packageName);
                String className = filePath.substring(i, filePath.length() - 6);
                //不是一个内部类
                boolean notInnerClass = className.indexOf("$") == -1;
                if (notInnerClass) {
                    //根据类名加载class对象
                    Class aClass = ClassUtils.forName(className);
                    if (aClass != null) {
                        classes.add(aClass);
                    }
                }
            }
        }
    }

    /**
     * 在jar文件中找出该文件夹中在package中的class
     *
     * @param packageName
     * @param filePath
     * @param classes
     */
    @SneakyThrows({IOException.class})
    static void getClassByJarPath(String packageName, String filePath, Set<Class> classes) {
        JarFile jarFile = new URLJarFile(new File(filePath));
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String jarEntryName = jarEntry.getName().replace(SLASH, DOT);
            //在package下的class
            boolean trueClass = jarEntryName.endsWith(CLASS_MARK) && jarEntryName.startsWith(packageName);
            //不是一个内部类
            boolean notInnerClass = jarEntryName.indexOf("$") == -1;
            if (trueClass && notInnerClass) {
                String className = jarEntryName.substring(0, jarEntryName.length() - 6);
                System.out.println(className);
                //根据类名加载class对象
                Class aClass = ClassUtils.forName(className);
                if (aClass != null) {
                    classes.add(aClass);
                }
            }
        }
    }
}
