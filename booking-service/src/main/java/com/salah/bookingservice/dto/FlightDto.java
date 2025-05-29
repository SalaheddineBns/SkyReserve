package com.salah.bookingservice.dto;

import lombok.Data;

import java.time.LocalDate;

public record FlightDto (
        Long flightId,
        String departureCity,
        String arrivalCity,
        LocalDate departureDate,
        Double price,
        Integer availableSeats){


}
