package com.salah.bookingservice.dto;

import java.time.LocalDateTime;
// status a voir !!! pending , confirmed, canceled
public record BookingDto(Long id, Long userId, Long flightId, int seats, Double totalPrice, LocalDateTime bookingDate,String status) {}
