package com.parking_spot_control.services;

import com.parking_spot_control.domain.Apartment;
import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.repositories.ApartmentRepository;
import com.parking_spot_control.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {


    @Autowired
    ApartmentRepository apartmentRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Transactional
    public Object add(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public List<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public Optional<Apartment> findById(Long id) {
        return apartmentRepository.findById(id);
    }

    public Optional<Apartment> findByNumber(Long number) {
        return apartmentRepository.findByNumber(number);
    }

    @Transactional
    public void delete(Apartment apartment) {
        apartmentRepository.delete(apartment);
    }
}
