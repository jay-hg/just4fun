package com.acai.just4fun.enums;

public enum BossCityCodeEnum {
    SHANGHAI("上海","101020100"),
    GUANGZHOU("广州","101280100"),
    SHENZHEN("深圳","101280600"),
    SUZHOU("苏州","101190400"),
    FUZHOU("福州","101230100");

    private String name;
    private String code;

    BossCityCodeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
