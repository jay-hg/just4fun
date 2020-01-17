package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public interface TypeHandler<T> {
    T getResult(Row row, int columnIndex);
}
