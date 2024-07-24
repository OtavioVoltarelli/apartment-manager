package com.apartment_manager.services;

import com.apartment_manager.domain.Apartment;
import com.apartment_manager.exceptions.ObjectNotFoundException;
import com.apartment_manager.repositories.ApartmentRepository;
import com.apartment_manager.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Apartment with id " + id + " not found"));
    }


    public Apartment findByNumber(Long number) {
        return apartmentRepository.findByNumber(number).orElseThrow(() -> new ObjectNotFoundException("Apartment with number " + number + " not found"));
    }


    @Transactional
    public void delete(Apartment apartment) {
        apartmentRepository.delete(apartment);
    }
}
