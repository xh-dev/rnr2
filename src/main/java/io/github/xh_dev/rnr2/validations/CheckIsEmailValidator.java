package io.github.xh_dev.rnr2.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CheckIsEmailValidator implements ConstraintValidator<CheckIsEmail, String> {
    CheckIsEmail annotation;
    @Override
    public void initialize(CheckIsEmail constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern email = Pattern.compile(this.annotation.regex());
        boolean rs = value != null && email.matcher(value).matches();
        if(!rs) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.annotation.message().replace("{{email}}", value==null?"": value)).addConstraintViolation();
            return false;
        }
        return true;
    }
}
