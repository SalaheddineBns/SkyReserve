package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.SeatReleaseRequestDto;
import com.salah.bookingservice.dto.SeatReservationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "inventory-service",url = "http://localhost:8081/api/inventory/")
public interface InventoryClient {

    @GetMapping("check")
    boolean checkAvailability(  @RequestParam Long flightId,
                                @RequestParam int seatsRequested);

    @PostMapping("/reserve")
    ResponseEntity<String> reserveSeats(@RequestBody SeatReservationRequestDto request);

    @GetMapping("available-seats")
    Integer getAvailableSeats(
            @RequestParam("flightId") Long flightId
    );

    @PostMapping("/release")
    ResponseEntity<String> releaseSeats(@RequestBody SeatReleaseRequestDto request);
}
