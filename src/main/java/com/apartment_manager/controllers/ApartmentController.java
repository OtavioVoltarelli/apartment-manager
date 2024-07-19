package com.apartment_manager.controllers;


import com.apartment_manager.domain.Apartment;
import com.apartment_manager.domain.Resident;
import com.apartment_manager.dtos.ApartmentDto;
import com.apartment_manager.dtos.ResidentRemovalDto;
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
    public ResponseEntity<Object> findById (Long id) {
        Optional<Apartment> apartment = apartmentService.findById(id);
        if(apartment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(apartment);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> updateApartment(@RequestBody @Valid ApartmentDto apartmentDto) {
        Optional<Apartment> apartmentOptional = apartmentService.findById(apartmentDto.getId());
        if (apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Apartment apartment = apartmentOptional.get();
        apartment.setNumber(apartmentDto.getNumber());
        apartment.setId(apartmentDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }
}
