package com.github.rich.common.core.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Petty
 * @see "Hibernate效验处理类"
 */
public class HibernateValidatorUtil {

    public static void validateModel(Object obj) {
        StringBuilder buffer = new StringBuilder();
        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
        //验证某个对象,，其实也可以只验证其中的某一个属性的
        Set<ConstraintViolation<Object>> constraintViolations = validator
                .validate(obj);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            if (buffer.length() == 0) {
                buffer.append(message);
            } else {
                buffer.append(",").append(message);
            }
        }
        if(!("".contentEquals(buffer))){
            throw new RuntimeException(buffer.toString());
        }
    }

}

