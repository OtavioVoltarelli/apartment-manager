package com.parking_spot_control.controllers;

import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.services.ResidentService;
import com.parking_spot_control.dtos.ResidentDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService residentService;


    @PostMapping
    public ResponseEntity<Object> saveResident(@RequestBody @Valid ResidentDto residentDto) {
        var resident = new Resident();
        BeanUtils.copyProperties(residentDto, resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.save(resident));
    }
}
