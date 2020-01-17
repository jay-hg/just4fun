package com.acai.just4fun.dto;


import com.acai.just4fun.annotation.Company;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployeeDTO implements BasicExcelDTO{
    @NotBlank(message = "[name]不能为空")
    private String name;

    private Integer age;

    @NotBlank(message = "[idCardNo]不能为空")
    private String idCardNo;

    @Company
    private String group;

    private String role;


}
