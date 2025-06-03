package com.salah.baggageservice.repository;

import com.salah.baggageservice.model.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    List<Baggage> findAllByBookingId(UUID bookingId);
}
