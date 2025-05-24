package com.salah.flightservice.dto;

import com.salah.flightservice.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class FlightResponseDto {
    private Long flightId;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private Double price;
    private Integer availableSeats;

    public FlightResponseDto(Flight flight, Integer availableSeats) {
        this.flightId = flight.getFlightId();
        this.departureCity = flight.getOrigin();
        this.arrivalCity = flight.getDestination();
        this.departureDate = flight.getDepartureTime().toLocalDate();
        this.price = flight.getPrice();
        this.availableSeats = availableSeats;
    }
}
