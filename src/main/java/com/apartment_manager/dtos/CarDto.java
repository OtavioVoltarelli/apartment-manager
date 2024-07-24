package com.apartment_manager.dtos;

import jakarta.validation.constraints.NotBlank;

public class CarDto {

    private Long id;
    @NotBlank
    private String model;
    @NotBlank
    private String color;
    @NotBlank
    private String licensePlate;
    private Long ownerID;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }
}
