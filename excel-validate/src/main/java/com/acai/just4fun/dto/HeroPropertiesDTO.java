package com.acai.just4fun.dto;

import com.acai.just4fun.enums.HeroTypeEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
public class HeroPropertiesDTO implements BasicExcelDTO{
    @NotBlank(message = "[name]不能为空")
    private String name;

    private HeroTypeEnum heroType;

    @Max(value = 100,message = "属性值不能超过100")
    private Integer value;
}
