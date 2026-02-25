package io.github.xh_dev.rnr2.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIsNotEmptyValidator implements ConstraintValidator<CheckIsNotEmpty, String> {
    CheckIsNotEmpty annotation;
    @Override
    public void initialize(CheckIsNotEmpty constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The field is null").addConstraintViolation();
            return false;
        } else if(value.trim().isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The field is empty").addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
