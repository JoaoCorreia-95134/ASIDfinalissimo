package com.iStudent.model.DTOs;

import com.iStudent.model.DTOs.base.PersonEntityDTO;

import java.time.LocalDate;

public class StudentDTO extends PersonEntityDTO {

    private final LocalDate enrollDate = LocalDate.now();

    private Long parent_id;

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public Long getParent() {
        return parent_id;
    }

    public void setParent(Long parent_id) {
        this.parent_id = parent_id;
    }
}
