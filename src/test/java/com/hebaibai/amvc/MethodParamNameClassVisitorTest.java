package com.hebaibai.amvc;

import com.hebaibai.amvc.utils.MethodParamNameClassVisitor;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodParamNameClassVisitorTest {

    @Test
    public void name() throws IOException {
        ClassReader classReader = new ClassReader("com.hebaibai.amvc.UrlMethodMapping");
        List<String> names = new ArrayList<>();
        MethodParamNameClassVisitor myClassVisitor = new MethodParamNameClassVisitor(
                names,
                "setParamNames",
                new Class[]{
                        String[].class
                }
        );
        classReader.accept(myClassVisitor, 0);
        for (String name : names) {
            System.out.println(name);
        }
    }
}