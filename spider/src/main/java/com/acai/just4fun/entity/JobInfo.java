package com.acai.just4fun.entity;

import com.acai.just4fun.enums.DataStatus;
import com.acai.just4fun.enums.JobSourceEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Class Description
 *
 * @author acai
 * @date 2019-10-09
 * @version 1.0.0
 */
@Data
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String detailUrl;

    private String jobType;

    private String salary;

    private String companyName;

    private String location;

    private String experience;

    private String education;

    private JobSourceEnum source;

    private Float minSalary;

    private Float maxSalary;

    private DataStatus dataStatus;
}
