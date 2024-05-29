package Asid.G1.gatewaydemo.model.DTOs;

import Asid.G1.gatewaydemo.model.DTOs.base.PersonEntityDTO;

import javax.validation.constraints.NotNull;

public class TeacherDTO extends PersonEntityDTO {

    @NotNull
    private SubjectDTO subject;

    public SubjectDTO getSubject() {
        return subject;
    }

    public void setSubject(SubjectDTO subject) {
        this.subject = subject;
    }
}
