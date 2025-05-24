package com.salah.flightservice.controller;

import com.salah.flightservice.dto.FlightResponseDto;
import com.salah.flightservice.model.Flight;
import com.salah.flightservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<FlightResponseDto> searchFlights(@RequestParam(required = false) String departureCity , @RequestParam(required = false) String arrivalCity, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(required = false) Integer seats) {
        return flightService.searchFlights(departureCity, arrivalCity, date,seats);
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
