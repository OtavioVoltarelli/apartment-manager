package com.parking_spot_control.dtos;

import jakarta.validation.constraints.NotBlank;



public class ResidentDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String apartment;

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank String getApartment() {
        return apartment;
    }

    public void setApartment(@NotBlank String apartment) {
        this.apartment = apartment;
    }
}

