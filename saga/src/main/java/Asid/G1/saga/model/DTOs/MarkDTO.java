package Asid.G1.saga.model.DTOs;

import Asid.G1.saga.model.entity.enums.MarkEnum;

import javax.validation.constraints.NotNull;

public class MarkDTO {

    @NotNull
    private MarkEnum mark;

    public MarkEnum getMark() {
        return mark;
    }

    public void setMark(MarkEnum mark) {
        this.mark = mark;
    }
}
