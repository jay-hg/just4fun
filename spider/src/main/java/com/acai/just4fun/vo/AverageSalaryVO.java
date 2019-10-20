package com.acai.just4fun.vo;

import lombok.Data;

@Data
public class AverageSalaryVO {
    //下限平均工资
    private int averageSalaryMin;
    //上限平均工资
    private int averageSalaryMax;
    private int averageSalary;
    //样本容量
    private int sampleSize;
}
