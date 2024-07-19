package com.apartment_manager.controllers;

import com.apartment_manager.domain.Apartment;
import com.apartment_manager.domain.Car;
import com.apartment_manager.domain.Resident;
import com.apartment_manager.dtos.CarRemovalDto;
import com.apartment_manager.dtos.ResidentRemovalDto;
import com.apartment_manager.services.ApartmentService;
import com.apartment_manager.services.CarService;
import com.apartment_manager.services.ResidentService;
import com.apartment_manager.dtos.ResidentDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @Autowired
    ApartmentService apartmentService;


    @Autowired
    CarService carService;


    @Transactional
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid ResidentDto residentDto) {
        var resident = new Resident();
        resident.setName(residentDto.getName());
        resident.setCpf(residentDto.getCpf());
        resident.setActivated(true);
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentDto.getApartmentNumber());
        if(apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        var apartment = apartmentOptional.get();
        apartment.setVacant(false);
        apartmentService.add(apartment);
        resident.setApartment(apartmentOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.add(resident));
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
        return ResponseEntity.status(HttpStatus.OK).body(residentOptional);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> update (@RequestBody @Valid ResidentDto residentDto) {
        Optional<Resident> residentOptional = residentService.findById(residentDto.getId());
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        Optional<Apartment> newApartmentOptional = apartmentService.findByNumber(residentDto.getApartmentNumber());
        if(newApartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Resident resident = residentOptional.get();
        Apartment newApartment = newApartmentOptional.get();
        Apartment oldApartment = resident.getApartment();

        resident.setId(residentOptional.get().getId());
        resident.setCpf(residentDto.getCpf());
        resident.setName(residentDto.getName());
        resident.setApartment(newApartmentOptional.get());

        if(newApartment.getNumber() != oldApartment.getNumber()) {
            oldApartment.getResidents().clear();
            oldApartment.setVacant(true);
            apartmentService.add(oldApartment);
            newApartment.setVacant(false);
        }

        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }


    @Transactional
    @DeleteMapping("/remove-all-residents")
    public ResponseEntity<Object> deleteAllResidentsFromApartment (@RequestBody @Valid ResidentRemovalDto residentRemovalDto) {
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentRemovalDto.getApartmentNumber());
        if (apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Apartment apartment = apartmentOptional.get();
        apartment.getResidents().forEach(resident -> resident.setApartment(null));
        apartment.getResidents().forEach(resident -> resident.setActivated(false));
        apartment.getResidents().clear();
        apartment.setVacant(true);
        apartmentService.add(apartment);
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }

    @Transactional
    @DeleteMapping("/remove-residents-by-cpf")
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
        residentToRemove.setActivated(false);
        apartment.getResidents().remove(residentToRemove);
        if (apartment.getResidents().isEmpty()) {
            apartment.setVacant(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }
}
