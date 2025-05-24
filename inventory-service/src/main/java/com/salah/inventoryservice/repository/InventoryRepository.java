package com.salah.inventoryservice.repository;

import com.salah.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    //for handling the null check with a good way
    Optional<Inventory> findByFlightIdAndFlightDate(Long flightId, LocalDate flightDate);
}
