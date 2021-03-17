package com.acai.just4fun.entity;

import lombok.Data;

@Data
public class Trader {
    private String name;
    private String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }
}
