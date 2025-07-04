package com.salah.aircraftservice.repository;

import com.salah.aircraftservice.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByAirline(String airline);

}