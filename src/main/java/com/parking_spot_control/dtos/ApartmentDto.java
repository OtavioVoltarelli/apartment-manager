package com.parking_spot_control.dtos;

import jakarta.validation.constraints.NotNull;

public class ApartmentDto {

    private Long id;

    @NotNull
    private Long number;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Long getNumber() {
        return number;
    }

    public void setNumber(@NotNull Long number) {
        this.number = number;
    }
}
