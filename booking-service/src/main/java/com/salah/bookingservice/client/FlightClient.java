package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "flight-service",url = "http://localhost:8081/flights")
public interface FlightClient {

    @GetMapping("/{id}/price")
     Double getPriceByFlightId(@RequestParam Long id) ;

    @GetMapping("/{id}")
    FlightDto getFlightById(@RequestParam Long id);
}
