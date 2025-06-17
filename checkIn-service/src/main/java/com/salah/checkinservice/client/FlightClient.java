package com.salah.checkinservice.client;

import com.salah.checkinservice.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/api/flights/{flightId}")
    FlightDto getFlight(@PathVariable Long flightId);
}