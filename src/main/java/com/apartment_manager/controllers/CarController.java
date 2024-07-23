package com.apartment_manager.controllers;

import com.apartment_manager.domain.Car;
import com.apartment_manager.domain.Resident;
import com.apartment_manager.dtos.CarDto;
import com.apartment_manager.dtos.CarRemovalDto;
import com.apartment_manager.services.CarService;
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
@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    ResidentService residentService;

    @Transactional
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid CarDto carDto) {
        var car = new Car();
        Resident owner = residentService.findById(carDto.getOwnerID());
        car.setOwnerId(owner);
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setActivated(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.add(car));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById (@PathVariable(value = "id") Long id) {
        Car car = carService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAll());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> update(@RequestBody @Valid CarDto carDto) {
        Car car = carService.findById(carDto.getId());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        if(carDto.getOwnerID() != null) {
            Resident resident = residentService.findById(carDto.getOwnerID());
            car.setOwnerId(resident);
        }
        return ResponseEntity.status(HttpStatus.OK).body(carService.add(car));
    }

    @Transactional
    @DeleteMapping("/remove-all-cars")
    public ResponseEntity<Object> deleteAllCarsFromResidents (@RequestBody @Valid CarRemovalDto carRemovalDto) {
        Resident resident = residentService.findByCpf(carRemovalDto.getCpf());
        resident.getCars().forEach(car -> car.setOwnerId(null));
        resident.getCars().forEach(car -> car.setActivated(false));
        resident.getCars().clear();
        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }

    @Transactional
    @DeleteMapping("/remove-car-by-license-plate")
    public ResponseEntity<Object> deleteCarFromResidentsByLicensePlate (@RequestBody @Valid CarRemovalDto carRemovalDto) {
        Resident resident = residentService.findByCpf(carRemovalDto.getCpf());
        Car car = carService.findByLicensePlate(carRemovalDto.getLicensePlate());
        car.setOwnerId(null);
        car.setActivated(false);
        resident.getCars().remove(car);
        residentService.add(resident);
        return ResponseEntity.status(HttpStatus.OK).body(carService.add(car));
    }
}
