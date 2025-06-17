package com.salah.checkinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {
    private Long flightId;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalTime departureTime; // 🕒 Heure de départ
    private LocalTime arrivalTime;   // 🕒 Heure d'arrivée
    private Integer availableSeats;
}
