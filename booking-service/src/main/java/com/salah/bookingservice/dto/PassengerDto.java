package com.salah.bookingservice.dto;

import java.util.List;

public record PassengerDto(
        String firstName,
        String lastName,
        String email,
        String phone,
        String civilite,
        List<BaggageOption> baggageOptions
) {}

