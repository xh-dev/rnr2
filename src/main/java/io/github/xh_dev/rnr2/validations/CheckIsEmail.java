package io.github.xh_dev.rnr2.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy =
        CheckIsEmailValidator.class
)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckIsEmail{
    String message() default "The email address[{{email}}] is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String regex() default "[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+";
}
