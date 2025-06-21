package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.SeatReleaseRequestDto;
import com.salah.bookingservice.dto.SeatReservationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "inventory-service")
public interface InventoryClient {



    @PostMapping("/api/inventory/reserve")
    ResponseEntity<String> reserveSeats(@RequestBody SeatReservationRequestDto request);

    @GetMapping("/api/inventory/available-seats")
    Integer getAvailableSeats(
            @RequestParam("flightId") Long flightId
    );

    @GetMapping("/api/inventory/check-availability")
    Boolean checkAvailability(@RequestParam("flightId") Long flightId,
                              @RequestParam("seatsRequested") int seatsRequested);



    @PostMapping("/api/inventory/release")
    ResponseEntity<String> releaseSeats(@RequestBody SeatReleaseRequestDto request);
}
