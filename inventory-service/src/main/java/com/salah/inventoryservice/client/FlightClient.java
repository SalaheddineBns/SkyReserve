package com.salah.inventoryservice.client;

import com.salah.inventoryservice.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "flight-service",url = "http://localhost:8081/flights")
public interface FlightClient {
    @GetMapping("/{id}")
    FlightDto getFlightById(@PathVariable("id") Long flightId);

    @GetMapping()
    List<FlightDto> getFlights();
}

