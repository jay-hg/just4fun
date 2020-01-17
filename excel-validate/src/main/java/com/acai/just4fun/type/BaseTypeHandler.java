package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.Row;

public abstract class BaseTypeHandler<T> implements TypeHandler<T> {

    @Override
    public T getResult(Row row, int columnIndex) {
        try {
            return getNullableResult(row, columnIndex);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultMapException("Error attempting to get column #" + columnIndex + " from result set.  Cause: " + e, e);
        }
    }


    public abstract T getNullableResult(Row row, int columnIndex);
}
