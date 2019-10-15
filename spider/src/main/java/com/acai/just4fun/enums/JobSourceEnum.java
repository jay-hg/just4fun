package com.acai.just4fun.enums;

public enum JobSourceEnum {
    OTHER("其他"),

    BOSS("boss直聘"),

    ZHILIAN("智联"),

    LAGOU("拉钩"),

    JOB51("51job");

    private String value;

    public String getValue() {
        return value;
    }

    JobSourceEnum(String value) {
        this.value = value;
    }
}
