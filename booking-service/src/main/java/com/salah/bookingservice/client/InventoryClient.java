package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.SeatReleaseRequestDto;
import com.salah.bookingservice.dto.SeatReservationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventory/check")
    boolean checkAvailability(  @RequestParam Long flightId,
                                @RequestParam int seatsRequested);

    @PostMapping("/api/inventory/reserve")
    ResponseEntity<String> reserveSeats(@RequestBody SeatReservationRequestDto request);

    @GetMapping("/api/inventory/available-seats")
    Integer getAvailableSeats(
            @RequestParam("flightId") Long flightId
    );

    @PostMapping("/api/inventory/release")
    ResponseEntity<String> releaseSeats(@RequestBody SeatReleaseRequestDto request);
}
