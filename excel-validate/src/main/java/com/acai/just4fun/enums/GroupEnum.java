package com.acai.just4fun.enums;

public enum GroupEnum {
    蜀汉,
    曹魏,
    东吴;

    public static boolean contains(String group) {
        for (GroupEnum ge : GroupEnum.values()) {
            if (ge.name().equals(group)) {
                return true;
            }
        }
        return false;
    }
}
