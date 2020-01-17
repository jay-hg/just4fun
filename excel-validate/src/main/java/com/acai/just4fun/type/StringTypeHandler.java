package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class StringTypeHandler extends BaseTypeHandler<String> {
    @Override
    public String getNullableResult(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }
        String str = cell.getStringCellValue();
        return str;
    }
}
