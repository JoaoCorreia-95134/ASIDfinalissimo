package Asid.G1.saga.model.entity;

import Asid.G1.saga.model.entity.base.BaseEntityWithIdLong;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject extends BaseEntityWithIdLong {

    @Column(unique = true, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
