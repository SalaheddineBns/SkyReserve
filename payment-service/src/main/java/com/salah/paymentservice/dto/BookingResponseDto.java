package com.salah.paymentservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponseDto(
        UUID bookingId,
        Long flightId,
        Integer seats,
        Double totalPrice,
        String status,
        LocalDateTime bookingDate,
        LocalDateTime expirationDate,
        Double baggagesFees,
        String firstName,
        String lastName,
        String email,
        String phone,
        String civility
) {}
