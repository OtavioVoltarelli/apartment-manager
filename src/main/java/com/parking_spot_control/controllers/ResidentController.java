package com.parking_spot_control.controllers;

import com.parking_spot_control.domain.Apartment;
import com.parking_spot_control.domain.Car;
import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.dtos.CarRemovalDto;
import com.parking_spot_control.services.ApartmentService;
import com.parking_spot_control.services.CarService;
import com.parking_spot_control.services.ResidentService;
import com.parking_spot_control.dtos.ResidentDto;
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
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentDto.getApartmentNumber());
        apartmentOptional.ifPresent(resident::setApartment);
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

    @PutMapping
    public ResponseEntity<Object> update (@RequestBody @Valid ResidentDto residentDto) {
        Optional<Resident> residentOptional = residentService.findById(residentDto.getId());
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        Optional<Apartment> apartmentOptional = apartmentService.findByNumber(residentDto.getApartmentNumber());
        if(apartmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found");
        }
        Resident resident = residentOptional.get();
        resident.setId(residentOptional.get().getId());
        resident.setCpf(residentDto.getCpf());
        resident.setName(residentDto.getName());
        resident.setApartment(apartmentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }

    @Transactional
    @PutMapping("/remove-all-cars")
    public ResponseEntity<Object> deleteAllCarsFromResidents (@RequestBody @Valid CarRemovalDto carRemovalDto) {
        Optional<Resident> residentOptional = residentService.findByCpf(carRemovalDto.getCpf());
        if (residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        Resident resident = residentOptional.get();
        resident.getCars().forEach(car -> car.setOwnerId(null));
        resident.getCars().clear();
        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }

    @Transactional
    @PutMapping("/remove-car-by-license-plate")
    public ResponseEntity<Object> deleteCarFromResidentsByLicensePlate (@RequestBody @Valid CarRemovalDto carRemovalDto) {
        Optional<Resident> residentOptional = residentService.findByCpf(carRemovalDto.getCpf());
        if (residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        Optional<Car> carOptional = carService.findByLicensePlate(carRemovalDto.getLicensePlate());
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("License plate not found");
        }
        Resident resident = residentOptional.get();
        Car carToRemove = carOptional.get();
        carToRemove.setOwnerId(null);
        resident.getCars().remove(carToRemove);
        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<Resident> residentOptional = residentService.findById(id);
        if(residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        residentService.delete(residentOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Resident deleted");
    }
}
