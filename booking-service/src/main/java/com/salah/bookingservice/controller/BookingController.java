package com.salah.bookingservice.controller;

import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Créer une réservation
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequest) {
        System.out.println(bookingRequest.toString());
        BookingResponseDto bookingDto = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(201).body(bookingDto); // 201 CREATED
    }

    // Récupérer toutes les réservations
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Récupérer une réservation par ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable UUID bookingId) {
        BookingResponseDto bookingDto = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(bookingDto);
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Void> updateBookingStatus(@PathVariable UUID bookingId, @RequestParam String status) {
        bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok().build();
    }



}
