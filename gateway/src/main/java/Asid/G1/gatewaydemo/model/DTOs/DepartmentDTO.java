package Asid.G1.gatewaydemo.model.DTOs;

import javax.validation.constraints.NotNull;

public class DepartmentDTO {

    private Long id;

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
