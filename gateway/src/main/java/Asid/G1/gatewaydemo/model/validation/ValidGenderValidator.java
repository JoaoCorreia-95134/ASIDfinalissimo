package Asid.G1.gatewaydemo.model.validation;

import Asid.G1.gatewaydemo.model.entity.enums.GenderEnum;

import javax.validation.*;


import java.util.Arrays;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, GenderEnum> {

    private GenderEnum[] values;

    @Override
    public void initialize(ValidGender constraintAnnotation) {
        this.values = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(GenderEnum genderEnum, ConstraintValidatorContext context) {
        return Arrays.asList(values).contains(genderEnum);
    }
}
