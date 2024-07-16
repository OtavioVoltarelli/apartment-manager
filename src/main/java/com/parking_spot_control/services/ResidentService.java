package com.parking_spot_control.services;

import com.parking_spot_control.domain.Resident;
import com.parking_spot_control.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentService {

    @Autowired
    ResidentRepository residentRepository;

    @Transactional
    public Object save(Resident resident) {
        return residentRepository.save(resident);
    }
}
