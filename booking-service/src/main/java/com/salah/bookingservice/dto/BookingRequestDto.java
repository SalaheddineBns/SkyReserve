package com.salah.bookingservice.dto;

import java.util.List;

public record BookingRequestDto(
        Long flightId,
        int seats,
        List<PassengerDto> passengers
) {}

