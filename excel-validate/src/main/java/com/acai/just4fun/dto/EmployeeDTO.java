package com.acai.just4fun.dto;


import com.acai.just4fun.annotation.Group;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployeeDTO {
    @NotBlank(message = "[name]不能为空")
    private String name;

    private Integer age;

    @NotBlank(message = "[idCardNo]不能为空")
    private String idCardNo;

    @Group
    private String group;

    private String role;


}