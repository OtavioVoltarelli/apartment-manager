package com.parking_spot_control.controllers;


import com.parking_spot_control.domain.Apartment;
import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.dtos.ApartmentDto;
import com.parking_spot_control.dtos.ResidentRemovalDto;
import com.parking_spot_control.services.ApartmentService;
import com.parking_spot_control.services.ResidentService;
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

    @Transactional
    @PutMapping("/remove-all-residents")
    public ResponseEntity<Object> deleteAllResidentsFromApartment (@RequestBody @Valid ResidentRemovalDto residentRemovalDto) {
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentRemovalDto.getApartmentNumber());
        if (apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Apartment apartment = apartmentOptional.get();
        apartment.getResidents().forEach(resident -> resident.setApartment(null));
        apartment.getResidents().clear();
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }

    @Transactional
    @PutMapping("/remove-residents-by-cpf")
    public ResponseEntity<Object> deleteResidentsFromApartmentByCpf(@RequestBody @Valid ResidentRemovalDto residentRemovalDto) {
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentRemovalDto.getApartmentNumber());
        if (apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Optional<Resident> residentOptional = residentService.findByCpf(residentRemovalDto.getResidentCpf());
        if (residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cpf not found");
        }
        Apartment apartment = apartmentOptional.get();
        Resident residentToRemove = residentOptional.get();
        residentToRemove.setApartment(null);
        apartment.getResidents().remove(residentToRemove);
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }
}
