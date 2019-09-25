package com.acai.just4fun.handler;

import com.acai.just4fun.annotation.Group;
import com.acai.just4fun.enums.GroupEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class GroupHandler implements Handler {
    private Field field;
    private String str;

    public GroupHandler(String str,Field field) {
        this.field = field;
        this.str = str;
    }

    @Override
    public String handle() {
        Group group = field.getAnnotation(Group.class);
        if (group != null) {
            if (StringUtils.isBlank(str)) {
                return group.message();
            } else if (!GroupEnum.contains(str)) {
                return "[group]字段非法取值";
            }
        }
        return null;
    }
}
