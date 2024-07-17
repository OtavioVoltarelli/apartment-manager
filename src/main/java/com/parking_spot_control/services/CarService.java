package com.parking_spot_control.services;

import com.parking_spot_control.domain.Car;
import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.repositories.CarRepository;
import com.parking_spot_control.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Transactional
    public Object add(Car car) {
        return carRepository.save(car);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Transactional
    public void delete(Car car) {
        carRepository.delete(car);
    }
}
