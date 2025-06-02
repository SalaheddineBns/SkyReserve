package com.salah.flightservice.client;

import com.salah.flightservice.dto.AircraftDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AircraftService")
public interface AircraftClient {

    @GetMapping("/api/aircrafts/{id}")
    AircraftDto getAircraftById(@PathVariable("id") Long id);
}
