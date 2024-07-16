package com.parking_spot_control.controllers;

import com.parking_spot_control.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService personService;
}
