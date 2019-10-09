package com.acai.just4fun.handler;

import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;

public interface Handler {
    public String handle(Field field, Cell cell);
}
