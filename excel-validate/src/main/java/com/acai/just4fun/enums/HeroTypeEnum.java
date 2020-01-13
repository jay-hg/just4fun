package com.acai.just4fun.enums;

public enum HeroTypeEnum {
    文("文"),
    武("武");

    private String value;

    HeroTypeEnum(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        for (HeroTypeEnum ge : HeroTypeEnum.values()) {
            if (ge.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
