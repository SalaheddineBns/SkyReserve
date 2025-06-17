package com.salah.checkinservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/seats-number/{flightId}")
    List<String> getAvailableSeats(@PathVariable Long flightId);

    @PutMapping("/api/inventory/seats-number/{flightId}")
    void assignSeat(@PathVariable("flightId") Long flightId,
                    @RequestParam("seatNumber") String seatNumber);

}