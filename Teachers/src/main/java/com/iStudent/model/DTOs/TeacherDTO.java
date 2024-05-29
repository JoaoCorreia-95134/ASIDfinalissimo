package com.iStudent.model.DTOs;

import com.iStudent.model.DTOs.base.PersonEntityDTO;
import com.iStudent.model.entity.Subject;

import javax.validation.constraints.NotNull;

public class TeacherDTO extends PersonEntityDTO {

    @NotNull
    private Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
