package com.iStudent.model.validation;


import com.iStudent.repository.ParentRepository;


import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

public class UsedEmailValidator implements ConstraintValidator<UsedEmail, String> {

      private final ParentRepository parentRepository;

    @Autowired
    public UsedEmailValidator(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return parentRepository.findByEmail(email).isPresent();
    }
}
