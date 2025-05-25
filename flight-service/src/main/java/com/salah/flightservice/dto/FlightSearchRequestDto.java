package com.salah.flightservice.dto;

import java.time.LocalDate;

public record FlightSearchRequestDto (
     String origin,
     String destination,
     LocalDate date,
        Integer numberOfPassengers // Remplace "seats"
){}

