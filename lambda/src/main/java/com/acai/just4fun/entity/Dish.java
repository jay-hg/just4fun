package com.acai.just4fun.entity;

import lombok.Data;

@Data
public class Dish {
    private String name;

    private int calories;

    private boolean vegetarian;

    private Type type;

    public enum Type { MEAT, FISH, OTHER}

    public Dish(String name, int calories, boolean vegetarian, Type type) {
        this.name = name;
        this.calories = calories;
        this.vegetarian = vegetarian;
        this.type = type;
    }
}
