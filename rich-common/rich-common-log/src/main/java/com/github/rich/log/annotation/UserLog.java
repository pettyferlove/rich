package com.github.rich.log.annotation;

import com.github.rich.log.constants.OperateType;

import java.lang.annotation.*;

/**
 * @author Petty
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface UserLog {

    String description() default "";

    OperateType type() default OperateType.OTHER;


}
