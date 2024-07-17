package com.parking_spot_control.services;

import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {

    @Autowired
    ResidentRepository residentRepository;


    @Transactional
    public Object add(Resident resident) {
        return residentRepository.save(resident);
    }

    public List<Resident> findAll() {
        return residentRepository.findAll();
    }

    public Optional<Resident> findById(Long id) {
        return residentRepository.findById(id);
    }

    @Transactional
    public void delete(Resident resident) {
        residentRepository.delete(resident);
    }
}
