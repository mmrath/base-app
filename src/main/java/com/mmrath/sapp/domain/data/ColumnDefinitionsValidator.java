package com.mmrath.sapp.domain.data;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ColumnDefinitionsValidator implements ConstraintValidator<ValidColumnDefinitions, List<ColumnDef>> {

    @Override
    public void initialize(ValidColumnDefinitions validColumnDefinitions) {

    }

    @Override
    public boolean isValid(List<ColumnDef> columnDefinitions, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
