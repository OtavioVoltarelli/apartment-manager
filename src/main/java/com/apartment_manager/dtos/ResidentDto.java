package com.apartment_manager.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ResidentDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotNull
    private Long apartmentNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public @NotNull Long getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(@NotNull Long apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}

