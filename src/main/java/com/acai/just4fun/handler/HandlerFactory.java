package com.acai.just4fun.handler;

import java.lang.annotation.Annotation;

public class HandlerFactory {
    public static Handler createHandler(Class<? extends Annotation> clazz) {
        switch (clazz.getTypeName()) {
            case "javax.validation.constraints.NotBlank":
                return new NotBlankHandler();
            case "com.acai.just4fun.annotation.Group":
                return new GroupHandler();
            default:
                return null;
        }
    }
}
