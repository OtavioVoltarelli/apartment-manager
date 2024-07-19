package com.apartment_manager.dtos;

import jakarta.validation.constraints.NotNull;

public class ResidentRemovalDto {

    @NotNull
    private Long apartmentNumber;

    private String residentCpf;



    public @NotNull Long getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(@NotNull Long apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getResidentCpf() {
        return residentCpf;
    }

    public void setResidentCpf(String residentCpf) {
        this.residentCpf = residentCpf;
    }
}
