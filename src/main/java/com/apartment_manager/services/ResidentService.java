package com.apartment_manager.services;

import com.apartment_manager.domain.Resident;
import com.apartment_manager.exceptions.ObjectNotFoundException;
import com.apartment_manager.repositories.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


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

    public Resident findById(Long id) {
        return residentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Resident with id " + id + " not found"));
    }

    public Resident findByCpf(String cpf) {
        return residentRepository.findByCpf(cpf).orElseThrow(() -> new ObjectNotFoundException("Resident with cpf " + cpf + " not found"));
    }


    @Transactional
    public void delete(Resident resident) {
        residentRepository.delete(resident);
    }
}
