package com.salah.flightservice.service;

import com.salah.flightservice.client.AircraftClient;
import com.salah.flightservice.client.InventoryClient;
import com.salah.flightservice.dto.AircraftDto;
import com.salah.flightservice.dto.FlightResponseDto;
import com.salah.flightservice.model.Flight;
import com.salah.flightservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private AircraftClient aircraftClient;


    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }


    public List<FlightResponseDto> searchFlights(String origin, String destination, LocalDate date, Integer numberOfPassengers) {
        LocalDateTime start = null, end = null;
        if (date != null) {
            start = date.atStartOfDay();
            end = start.plusDays(1);
        }

        System.out.println(origin + " " + destination + " " + start + " " + end + " $$$$$$$$$$"+numberOfPassengers);

        List<Flight> flights = flightRepository.searchFlights(origin, destination, start, end);

        return flights.stream()
                .map(flight -> {
                    Integer availableSeats = inventoryClient.getAvailableSeats(flight.getFlightId());

                    if (numberOfPassengers != null && (availableSeats == null || availableSeats < numberOfPassengers)) {
                        return null; // Pas assez de sièges
                    }

                    AircraftDto aircraft = null;
                    try {
                        aircraft = aircraftClient.getAircraftById(flight.getAircraftId());
                    } catch (Exception e) {
                        System.out.println("Aircraft not found for flight: " + flight.getFlightId());
                    }

                    return FlightResponseDto.builder()
                            .flightId(flight.getFlightId())
                            .flightNumber(flight.getFlightNumber())
                            .origin(flight.getOrigin())
                            .destination(flight.getDestination())
                            .departureDate(flight.getDepartureTime().toLocalDate())
                            .departureTime(flight.getDepartureTime().toLocalTime())
                            .arrivalTime(flight.getArrivalTime().toLocalTime())
                            .price(flight.getPrice())
                            .availableSeats(availableSeats)
                            .aircraftId(flight.getAircraftId())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

}
