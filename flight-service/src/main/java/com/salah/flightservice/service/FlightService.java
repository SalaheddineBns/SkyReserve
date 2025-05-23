package com.salah.flightservice.service;

import com.salah.flightservice.model.Flight;
import com.salah.flightservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDate date) {
        LocalDateTime start=null, end=null;
        if(date!=null) {
            start=date.atStartOfDay();
            end=start.plusDays(1);
        }
        return flightRepository.searchFlights(origin, destination, start, end);
    }
}
