package com.acai.just4fun.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;

public class NotBlankHandler implements Handler {

    @Override
    public String handle(Field field, Cell cell) {
        NotBlank notBlank = field.getAnnotation(NotBlank.class);
        if (notBlank != null && cellIsBlank(cell)) {
            return notBlank.message();
        }

        return null;
    }

    protected boolean cellIsBlank(Cell cell) {
        if (cell == null) {
            return true;
        }
        return cellIsString(cell) && StringUtils.isBlank(cell.getStringCellValue());
    }

    protected boolean cellIsString(Cell cell) {
        return cell != null && cell.getCellTypeEnum().equals(CellType.STRING);
    }
}
