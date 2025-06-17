package com.salah.bookingservice.repository;

import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByBooking(Booking booking);
}
