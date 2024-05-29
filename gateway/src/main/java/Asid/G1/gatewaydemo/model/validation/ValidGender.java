package Asid.G1.gatewaydemo.model.validation;

import Asid.G1.gatewaydemo.model.entity.enums.GenderEnum;

import javax.validation.*;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = ValidGenderValidator.class)
public @interface ValidGender {
    GenderEnum[] anyOf();

    String message() default "Invalid Gender";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
