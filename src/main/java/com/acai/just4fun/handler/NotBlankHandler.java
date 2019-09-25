package com.acai.just4fun.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

public class NotBlankHandler implements Handler {

    @Override
    public String handle(Field field,String str) {
        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (notBlank != null && StringUtils.isBlank(str)) {
            return notBlank.message();
        }
        return null;
    }
}
