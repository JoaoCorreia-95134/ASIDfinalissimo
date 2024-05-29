package Asid.G1.gatewaydemo.model.DTOs.base;


import Asid.G1.gatewaydemo.model.entity.enums.GenderEnum;
import Asid.G1.gatewaydemo.model.DTOs.TownDTO;
import Asid.G1.gatewaydemo.model.validation.ValidGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

import javax.validation.constraints.*;

public abstract class PersonEntityDTO {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String middleName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    private String EGN;

    @Positive
    @Min(14)
    private int age;

    @ValidGender(anyOf = {GenderEnum.M, GenderEnum.F})
    private GenderEnum genderEnum;

    @NotNull
    private Long town_id;

    @NotNull
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEGN() {
        return EGN;
    }

    public void setEGN(String EGN) {
        this.EGN = EGN;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenderEnum getGender() {
        return genderEnum;
    }

    public void setGender(GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public Long getTown() {
        return town_id;
    }

    public void setTown(Long town) {
        this.town_id = town;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
