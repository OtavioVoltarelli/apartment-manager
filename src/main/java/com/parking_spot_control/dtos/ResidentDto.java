package com.parking_spot_control.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;



@Getter
@Setter
public class ResidentDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cpf;
    @NotBlank
    private String apartment;
}

