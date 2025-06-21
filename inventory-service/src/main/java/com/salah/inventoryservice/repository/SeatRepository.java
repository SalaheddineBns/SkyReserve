package com.salah.inventoryservice.repository;

import com.salah.inventoryservice.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByInventory_FlightIdAndIsAvailableTrue(Long flightId);
    Optional<Seat> findBySeatNumberAndInventory_FlightId(String seatNumber, Long flightId);
}

