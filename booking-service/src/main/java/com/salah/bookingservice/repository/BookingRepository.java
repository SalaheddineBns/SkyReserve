package com.salah.bookingservice.repository;

import com.salah.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    //List<Booking> findAllByUserId(Long userId);
    List<Booking> findByStatusAndExpirationDateBefore(String status, LocalDateTime time);

}
