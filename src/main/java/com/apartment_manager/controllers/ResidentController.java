package com.apartment_manager.controllers;

import com.apartment_manager.domain.Apartment;
import com.apartment_manager.domain.Resident;
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
        Apartment apartment = apartmentService.findByNumber(residentDto.getApartmentNumber());
        apartment.setVacant(false);
        apartmentService.add(apartment);
        resident.setApartment(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.add(resident));
    }

    @GetMapping
    public ResponseEntity<List<Resident>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(residentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById (@PathVariable(value = "id") Long id) {
        Resident resident = residentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resident);
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Object> update (@RequestBody @Valid ResidentDto residentDto) {
        Resident resident = residentService.findById(residentDto.getId());
        Apartment newApartment = apartmentService.findByNumber(residentDto.getApartmentNumber());
        Apartment oldApartment = resident.getApartment();

        resident.setCpf(residentDto.getCpf());
        resident.setName(residentDto.getName());

        if(newApartment.getNumber() != oldApartment.getNumber()) {
            resident.setApartment(newApartment);
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
        Apartment apartment = apartmentService.findByNumber(residentRemovalDto.getApartmentNumber());
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
        Apartment apartment = apartmentService.findByNumber(residentRemovalDto.getApartmentNumber());
        Resident resident = residentService.findByCpf(residentRemovalDto.getResidentCpf());
        resident.setApartment(null);
        resident.setActivated(false);
        apartment.getResidents().remove(resident);
        if (apartment.getResidents().isEmpty()) {
            apartment.setVacant(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.add(apartment));
    }
}
