package com.parking_spot_control.services;

import com.parking_spot_control.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ResidentService {

    @Autowired
    ResidentRepository residentRepository;
}
