package com.github.rich.attachment.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StorageTypeValidator.class)
@Documented
public @interface StorageTypeConstraint {

    String message() default "储存类型错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
