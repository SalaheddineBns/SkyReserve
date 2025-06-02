package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightClient {

    @GetMapping("/flights/{id}/price")
    Double getPriceByFlightId(@PathVariable("id") Long id);

    @GetMapping("/flights/{id}")
    FlightDto getFlightById(@PathVariable("id") Long id);
}

