package com.salah.checkinservice.client;

import com.salah.checkinservice.dto.SeatAssignmentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/seats-number/{flightId}")
    List<String> getAvailableSeats(@PathVariable Long flightId);

    @PutMapping("/api/inventory/seats/assign")
    void assignSeatToPassenger(@RequestBody SeatAssignmentRequestDto request);
}
