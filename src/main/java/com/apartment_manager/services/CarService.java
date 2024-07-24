package com.apartment_manager.services;

import com.apartment_manager.domain.Car;
import com.apartment_manager.exceptions.ObjectNotFoundException;
import com.apartment_manager.repositories.CarRepository;
import com.apartment_manager.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Car with id " + id + " not found"));
    }


    @Transactional
    public void delete(Car car) {
        carRepository.delete(car);
    }

    public Car findByLicensePlate(String licensePlate) {
        return carRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new ObjectNotFoundException("Car with license plate " + licensePlate + " not found"));
    }

}
