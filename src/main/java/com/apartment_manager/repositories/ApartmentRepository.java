package com.apartment_manager.repositories;

import com.apartment_manager.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    public Optional<Apartment> findByNumber(Long number);
}
