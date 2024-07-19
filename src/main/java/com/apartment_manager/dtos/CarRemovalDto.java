package com.apartment_manager.dtos;

import jakarta.validation.constraints.NotBlank;

public class CarRemovalDto {

    @NotBlank
    private String cpf;

    private String licensePlate;


    public @NotBlank String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank String cpf) {
        this.cpf = cpf;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
