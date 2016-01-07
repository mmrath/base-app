package com.mmrath.sapp.domain.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Created by murali on 6/01/2016.
 */
public class ColumnDefinitionsValidator implements ConstraintValidator<ValidColumnDefinitions, List<ColumnDefinition>> {

    @Override
    public void initialize(ValidColumnDefinitions validColumnDefinitions) {

    }

    @Override
    public boolean isValid(List<ColumnDefinition> columnDefinitions, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
