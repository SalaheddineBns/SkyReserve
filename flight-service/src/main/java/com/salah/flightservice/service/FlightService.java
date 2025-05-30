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

    // search by seats ?
//    public List<FlightResponseDto> searchFlightsBySeats(String origin, String destination, LocalDate date, Integer seats) {
//        LocalDateTime start=null, end=null;
//        if(date!=null) {
//            start=date.atStartOfDay();
//            end=start.plusDays(1);
//        }
//       List<Flight> flights=flightRepository.searchFlights(origin, destination, start, end);
//       return flights.stream().map(flight -> {
//           boolean isAvailable=seats !=null ? inventoryClient.checkAvailability(flight.getFlightId(),date,seats):true;
//           return new FlightResponseDto(flight,isAvailable);
//       }).collect(Collectors.toList());
//    }
//     search by returning all available seats
    public List<FlightResponseDto> searchFlights(String origin, String destination, LocalDate date, Integer numberOfPassengers) {
        LocalDateTime start = null, end = null;
        if (date != null) {
            start = date.atStartOfDay();
            end = start.plusDays(1);
        }

        List<Flight> flights = flightRepository.searchFlights(origin, destination, start, end);

        return flights.stream()
                .map(flight -> {
                    Integer availableSeats = null;

                    // Si la date est spécifiée, on récupère la dispo des sièges

                        availableSeats = inventoryClient.getAvailableSeats(flight.getFlightId());


                    // Vérifier les sièges seulement si "seats" demandé ET dispo connue
                    boolean hasEnoughSeats = true;
                    if (numberOfPassengers != null) {
                        // Si on ne connaît pas la dispo, on exclut ce vol (hasEnoughSeats = false)
                        hasEnoughSeats = (availableSeats != null) && (availableSeats >= numberOfPassengers);
                    }

                    if (hasEnoughSeats) {
                        AircraftDto aircraft = null;
                        try {
                            aircraft = aircraftClient.getAircraftById(flight.getAircraftId());

                        } catch (Exception e) {
                            System.out.println("Aircraft not found for flight: " + flight.getFlightId());
                        }

                        return FlightResponseDto.builder()
                                .flightId(flight.getFlightId())
                                .flightNumber(flight.getFlightNumber())
                                .departureCity(flight.getOrigin())
                                .arrivalCity(flight.getDestination())
                                .departureDate(flight.getDepartureTime().toLocalDate())
                                .price(flight.getPrice())
                                .availableSeats(availableSeats)
                                .aircraft(aircraft)  // Objets de FeignClient
                                .build();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }



}
