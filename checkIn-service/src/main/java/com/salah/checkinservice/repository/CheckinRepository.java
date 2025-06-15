package com.salah.checkinservice.repository;

import com.salah.checkinservice.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CheckinRepository extends JpaRepository<Checkin, UUID> {
    List<Checkin> findByBookingId(UUID bookingId);
    Optional<Checkin> findByBoardingPassNumber(UUID boardingPassNumber);

}