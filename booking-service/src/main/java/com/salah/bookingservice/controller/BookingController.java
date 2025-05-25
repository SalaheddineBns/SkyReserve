package com.salah.bookingservice.controller;

import com.salah.bookingservice.dto.BookingDto;
import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Créer une réservation
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequestDto bookingRequest) {
        BookingDto bookingDto = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(201).body(bookingDto); // 201 CREATED
    }

    // Récupérer toutes les réservations
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
}
