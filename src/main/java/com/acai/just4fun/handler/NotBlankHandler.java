package com.acai.just4fun.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

public class NotBlankHandler implements Handler {
    private String str;
    private Field field;

    public NotBlankHandler(String str, Field field) {
        this.str = str;
        this.field = field;
    }

    @Override
    public String handle() {
        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (notBlank != null && StringUtils.isBlank(str)) {
            return notBlank.message();
        }
        return null;
    }
}
