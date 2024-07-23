package com.apartment_manager.controllers;


import com.apartment_manager.domain.Apartment;
import com.apartment_manager.dtos.ApartmentDto;
import com.apartment_manager.services.ApartmentService;
import com.apartment_manager.services.ResidentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    ResidentService residentService;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> add (@RequestBody @Valid ApartmentDto apartmentDto) {
        var apartment = new Apartment();
        apartment.setNumber(apartmentDto.getNumber());
        apartment.setVacant(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.add(apartment));
    }

    @GetMapping
    public ResponseEntity<List<Apartment>> findAll () {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById (@PathVariable(value = "id")Long id) {
        Apartment apartment = apartmentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(apartment);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> updateApartment(@RequestBody @Valid ApartmentDto apartmentDto) {
        Apartment apartment = apartmentService.findById(apartmentDto.getId());
        apartment.setNumber(apartmentDto.getNumber());
        apartment.setId(apartmentDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApartment(@PathVariable(value = "id")Long id) {
        Apartment apartment = apartmentService.findById(id);
        apartmentService.delete(apartment);
        return ResponseEntity.status(HttpStatus.OK).body("Apartment removed!");
    }
}
