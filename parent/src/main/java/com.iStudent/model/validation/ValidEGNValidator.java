package com.iStudent.model.validation;

import javax.validation.*;


public class ValidEGNValidator implements ConstraintValidator<ValidEGN, String> {


    @Override
    public boolean isValid(String egn, ConstraintValidatorContext context) {
        return egn.length() == 10;
    }
}
