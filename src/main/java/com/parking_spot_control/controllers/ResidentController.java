package com.parking_spot_control.controllers;

import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.services.ResidentService;
import com.parking_spot_control.dtos.ResidentDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService residentService;


    @PostMapping
    public ResponseEntity<Object> addResident(@RequestBody @Valid ResidentDto residentDto) {
        var resident = new Resident();
        BeanUtils.copyProperties(residentDto, resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.addResident(resident));
    }

    @GetMapping
    public ResponseEntity<List<Resident>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(residentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById (@PathVariable(value = "id") Long id) {
        Optional<Resident> residentOptional = residentService.findById(id);
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(residentOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> attResident(@PathVariable(value = "id") Long id, @RequestBody @Valid ResidentDto residentDto) {
        Optional<Resident> residentOptional = residentService.findById(id);
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        var resident = new Resident();
        BeanUtils.copyProperties(residentDto, resident);
        resident.setId(residentOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(residentService.addResident(resident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteResident(@PathVariable(value = "id") Long id) {
        Optional<Resident> residentOptional = residentService.findById(id);
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        residentService.deleteResident(residentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Resident deleted");
    }
}
