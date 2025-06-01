package com.salah.flightservice.controller;

import com.salah.flightservice.dto.FlightResponseDto;
import com.salah.flightservice.model.Flight;
import com.salah.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("flights")
public class FlightController {

    @Autowired
    FlightService flightService;


    @GetMapping
    public List<Flight> getFlights() {
        return flightService.getFlights();
    }

    @GetMapping("/search")
    public List<FlightResponseDto> searchFlights(@RequestParam(required = false) String origin , @RequestParam(required = false) String arrivalCity, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(required = false) Integer numberOfPassengers) {
        return flightService.searchFlights(origin, arrivalCity, date,numberOfPassengers);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlights().stream().filter(flight -> flight.getFlightId().equals(id)).findFirst().orElse(null);
    }

    @GetMapping("/{id}/price")
    public Double getPriceByFlightId(@PathVariable Long id) {
        return flightService.getFlights().stream().filter(flight -> flight.getFlightId().equals(id)).findFirst().map(Flight::getPrice).orElse(null);
    }

    @PostMapping()
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }
//
//    @GetMapping("/{id}")
//    public String getFlightById() {
//        return "flight";
//    }
//    @GetMapping("/search")
//    public String searchFlights() {
//        return "flights";
//    }
//    @GetMapping("/{id}/details")
//    public String getFlightDetails() {
//        return "flight details";
//    }
//    @GetMapping("/{id}/booking")
//    public String bookFlight() {
//        return "booking";
//    }
}
