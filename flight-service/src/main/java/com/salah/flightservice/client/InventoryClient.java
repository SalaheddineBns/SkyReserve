package com.salah.flightservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "inventory-service",url = "http://localhost:8082")
public interface InventoryClient {

    @GetMapping("/api/inventory/check")
     boolean checkAvailability(@RequestParam("flightId") Long flightId,
                               @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               @RequestParam("seats") int seats);
    @GetMapping("/api/inventory/available-seats")
    Integer getAvailableSeats(
            @RequestParam("flightId") Long flightId
    );
}
