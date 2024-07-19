package com.apartment_manager.controllers;

import com.apartment_manager.domain.Car;
import com.apartment_manager.domain.Resident;
import com.apartment_manager.dtos.CarDto;
import com.apartment_manager.dtos.CarRemovalDto;
import com.apartment_manager.services.CarService;
import com.apartment_manager.services.ResidentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
        Optional<Resident> ownerOptional = residentService.findById(carDto.getOwnerID());
        ownerOptional.ifPresent(car::setOwnerId);
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setActivated(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.add(car));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById (@PathVariable(value = "id") Long id) {
        Optional<Car> carOptional = carService.findById(id);
        if(carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(carOptional);
    }

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(carService.findAll());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Object> update(@RequestBody @Valid CarDto carDto) {
        Optional<Car> carOptional = carService.findById(carDto.getId());
        if(carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
        Car car = carOptional.get();
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        if(carDto.getOwnerID() != null) {
            Optional<Resident> residentOptional = residentService.findById(carDto.getOwnerID());
            if(residentOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
            }
            car.setOwnerId(residentOptional.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(carService.add(car));
    }

    @Transactional
    @DeleteMapping("/remove-all-cars")
    public ResponseEntity<Object> deleteAllCarsFromResidents (@RequestBody @Valid CarRemovalDto carRemovalDto) {
        Optional<Resident> residentOptional = residentService.findByCpf(carRemovalDto.getCpf());
        if (residentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resident not found");
        }
        Resident resident = residentOptional.get();
        resident.getCars().forEach(car -> car.setOwnerId(null));
        resident.getCars().forEach(car -> car.setActivated(false));
        resident.getCars().clear();
        return ResponseEntity.status(HttpStatus.OK).body(residentService.add(resident));
    }

    @Transactional
    @DeleteMapping("/remove-car-by-license-plate")
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
        carToRemove.setActivated(false);
        resident.getCars().remove(carToRemove);
        residentService.add(resident);
        return ResponseEntity.status(HttpStatus.OK).body(carService.add(carToRemove));
    }
}
