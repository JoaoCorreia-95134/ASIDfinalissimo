package Asid.G1.gatewaydemo.model.DTOs;

import Asid.G1.gatewaydemo.model.DTOs.base.PersonEntityDTO;
import jakarta.validation.constraints.Positive;

import javax.validation.constraints.*;

public class EmployeeDTO extends PersonEntityDTO {

    @NotNull
    private String jobTitle;

    @Positive
    @Min(6)
    @Max(8)
    private int workHours;

    @NotNull
    private DepartmentDTO department;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }
}
