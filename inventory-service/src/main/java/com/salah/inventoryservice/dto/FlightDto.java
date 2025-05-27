package com.salah.inventoryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Flight information retrieved from FlightService.
 */
@Data
public class FlightDto {

    private Long flightId;       // Identifiant unique du vol
    private String flightNumber; // Ex: AF123
    private String origin;       // Ex: Paris
    private String destination;  // Ex: New York
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;        // Prix du siège
}
