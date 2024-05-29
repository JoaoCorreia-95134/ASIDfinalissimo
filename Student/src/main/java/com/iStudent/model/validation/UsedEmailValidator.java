package com.iStudent.model.validation;


import com.iStudent.repository.StudentRepository;


import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;

public class UsedEmailValidator implements ConstraintValidator<UsedEmail, String> {

    private final StudentRepository studentRepository;

    @Autowired
    public UsedEmailValidator(StudentRepository studentRepository
) {
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return studentRepository.findByEmail(email).isPresent();
    }
}
