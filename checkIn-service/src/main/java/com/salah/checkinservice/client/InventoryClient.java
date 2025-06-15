package com.salah.checkinservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/seats/{flightId}")
    List<String> getAvailableSeats(@PathVariable Long flightId);

    @PutMapping("/api/inventory/assign-seat")
    void assignSeat(@RequestParam Long flightId, @RequestParam String seatNumber);
}