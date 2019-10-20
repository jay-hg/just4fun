package com.acai.just4fun.enums;

public enum DataStatus {
    USEABLE("可用的"),
    INVALID("无效的"),
    NEED_HANDLE("待处理");
    private String value;

    public String getValue() {
        return value;
    }

    DataStatus(String value) {
        this.value = value;
    }
}
