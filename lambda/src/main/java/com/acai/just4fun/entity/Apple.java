package com.acai.just4fun.entity;

import lombok.Data;

@Data
public class Apple {
    private int color;
    private float weight;

    public Apple(int color, float weight) {
        this.color = color;
        this.weight = weight;
    }
}
