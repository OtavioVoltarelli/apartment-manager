package com.parking_spot_control.dtos;

import com.parking_spot_control.domain.Resident;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarDto {

    @NotBlank
    private String model;
    @NotBlank
    private String color;
    @NotBlank
    private String licensePlate;
    @NotNull
    private Long ownerID;

    public @NotBlank String getModel() {
        return model;
    }

    public void setModel(@NotBlank String model) {
        this.model = model;
    }

    public @NotBlank String getColor() {
        return color;
    }

    public void setColor(@NotBlank String color) {
        this.color = color;
    }

    public @NotBlank String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotBlank String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public @NotNull Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(@NotNull Long ownerID) {
        this.ownerID = ownerID;
    }
}
