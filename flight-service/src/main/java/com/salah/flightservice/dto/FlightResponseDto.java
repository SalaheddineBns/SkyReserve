package com.salah.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {
    private Long flightId;
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private Double price;
    private Integer availableSeats;
    private AircraftDto aircraft;
}
