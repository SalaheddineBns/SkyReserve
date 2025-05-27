package com.salah.inventoryservice.repository;

import com.salah.inventoryservice.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
