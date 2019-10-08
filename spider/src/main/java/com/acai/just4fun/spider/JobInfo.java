package com.acai.just4fun.spider;

import lombok.Data;

@Data
public class JobInfo {
    private String detailUrl;
    private String jobType;
    private String salary;
    private String companyName;
    private String location;
    private String experience;
    private String education;
}
