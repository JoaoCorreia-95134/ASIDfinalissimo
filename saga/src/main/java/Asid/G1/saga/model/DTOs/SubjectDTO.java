package Asid.G1.saga.model.DTOs;

import javax.validation.constraints.NotNull;

public class SubjectDTO {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
