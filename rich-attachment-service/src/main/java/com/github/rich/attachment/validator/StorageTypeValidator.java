package com.github.rich.attachment.validator;

import com.github.rich.attachment.constants.StorageTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Petty
 */
public class StorageTypeValidator implements ConstraintValidator<StorageTypeConstraint,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return StorageTypeEnum.isInEnum(value);
    }
}
