package com.salah.aircraftservice.repository;

import com.salah.aircraftservice.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}