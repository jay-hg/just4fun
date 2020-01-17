package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class IntegerTypeHandler extends BaseTypeHandler<Integer> {
    @Override
    public Integer getNullableResult(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return null;
        }
        Double num = cell.getNumericCellValue();
        return num.intValue();
    }
}
