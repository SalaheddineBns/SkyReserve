package com.salah.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {
    private Long flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime; // 🕒 Heure de départ
    private LocalTime arrivalTime;   // 🕒 Heure d'arrivée
    private Double price;
    private Integer availableSeats;
    private Long aircraftId;
}
