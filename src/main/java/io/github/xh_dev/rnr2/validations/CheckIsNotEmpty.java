package io.github.xh_dev.rnr2.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy =
        CheckIsNotEmptyValidator.class
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckIsNotEmpty {
    String message() default "The field is null or empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
