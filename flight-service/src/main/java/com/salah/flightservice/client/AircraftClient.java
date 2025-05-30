package com.salah.flightservice.client;

import com.salah.flightservice.dto.AircraftDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AircraftService" , url = "http://localhost:8082")
public interface AircraftClient {

    @GetMapping("/api/aircrafts/{id}")
    AircraftDto getAircraftById(@PathVariable("id") Long id);
}
