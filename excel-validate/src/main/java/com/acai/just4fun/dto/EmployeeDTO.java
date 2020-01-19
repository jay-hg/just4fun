package com.acai.just4fun.dto;


import com.acai.just4fun.annotation.Company;
import com.acai.just4fun.type.ExcelCol;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployeeDTO {
    @ExcelCol(0)
    @NotBlank(message = "[name]不能为空")
    private String name;

    @ExcelCol(1)
    private Integer age;

    @ExcelCol(2)
    @NotBlank(message = "[idCardNo]不能为空")
    private String idCardNo;

    @ExcelCol(3)
    @Company
    private String group;

    @ExcelCol(4)
    private String role;


}
