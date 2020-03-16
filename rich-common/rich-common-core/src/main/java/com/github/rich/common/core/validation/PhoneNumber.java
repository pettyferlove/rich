package com.github.rich.common.core.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * 验证是否为手机号码
 * @author Petty
 */
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "Invalid phone number";
    Class[] groups() default {};
    Class[] payload() default {};
}
