package com.hebaibai.amvc.namegetter;

import org.junit.Test;

public class AsmParamNameGetterTest {

    @Test
    public void name() {

        AsmParamNameGetter asmParamNameGetter = new AsmParamNameGetter();
        asmParamNameGetter.getClassReader(AsmParamNameGetter.class);
    }
}