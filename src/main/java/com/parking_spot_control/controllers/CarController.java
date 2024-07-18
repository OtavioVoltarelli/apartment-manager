package com.parking_spot_control.controllers;

import com.parking_spot_control.domain.Car;
import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.dtos.CarDto;
import com.parking_spot_control.services.CarService;
import com.parking_spot_control.services.ResidentService;
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
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setLicensePlate(carDto.getLicensePlate());
        Optional<Resident> ownerOptional = residentService.findById(carDto.getOwnerID());
        ownerOptional.ifPresent(car::setOwnerId);
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
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id, @RequestBody @Valid CarDto carDto) {
        Optional<Car> carOptional = carService.findById(id);
        if(carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
        var car = new Car();
        BeanUtils.copyProperties(carDto, car);
        car.setId(carOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(carService.add(car));
    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        Optional<Car> carOptional = carService.findById(id);
        if(carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
        carService.delete(carOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Car deleted");
    }
}
