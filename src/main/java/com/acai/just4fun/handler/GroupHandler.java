package com.acai.just4fun.handler;

import com.acai.just4fun.annotation.Group;
import com.acai.just4fun.enums.GroupEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.reflect.Field;

public class GroupHandler extends NotBlankHandler implements Handler {

    @Override
    public String handle(Field field, Cell cell) {
        Group group = field.getAnnotation(Group.class);
        if (group != null) {
            if (cellIsBlank(cell) || !cellIsString(cell)) {
                return group.message();
            } else if (!GroupEnum.contains(cell.getStringCellValue())) {
                return "[group]字段非法取值";
            }
        }
        return null;
    }
}
