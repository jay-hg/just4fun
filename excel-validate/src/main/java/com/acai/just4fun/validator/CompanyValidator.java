package com.acai.just4fun.validator;

import com.acai.just4fun.annotation.Company;
import com.acai.just4fun.enums.GroupEnum;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyValidator implements ConstraintValidator<Company, String> {
    @Override
    public void initialize(Company constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        } else if (!GroupEnum.contains(value)) {
            return false;
        }
        return true;
    }
}
