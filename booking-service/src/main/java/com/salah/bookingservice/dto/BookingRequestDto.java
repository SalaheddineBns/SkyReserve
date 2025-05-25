package com.salah.bookingservice.dto;


public record BookingRequestDto(
        Long flightId,
        Long userId,
        Integer seats
) {}

