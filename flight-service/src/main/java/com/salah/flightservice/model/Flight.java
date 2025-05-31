package com.salah.flightservice.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.salah.flightservice.dto.AircraftDto;

@Entity
@Data
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime departureTime; // 📅 + 🕒 ensemble

    @Column(nullable = false)
    private LocalDateTime arrivalTime;   // 📅 + 🕒 ensemble

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Long aircraftId;

    @Builder
    public Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, LocalDateTime arrivalTime, Double price, Long aircraftId) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.aircraftId = aircraftId;
    }
}
