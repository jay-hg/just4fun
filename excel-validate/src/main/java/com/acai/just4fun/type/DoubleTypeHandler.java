package com.acai.just4fun.type;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DoubleTypeHandler extends BaseTypeHandler<Double> {
    @Override
    public Double getNullableResult(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        Double num = cell.getNumericCellValue();
        return num;
    }
}
