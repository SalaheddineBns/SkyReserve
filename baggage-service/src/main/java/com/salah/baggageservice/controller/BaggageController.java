package com.salah.baggageservice.controller;

import com.salah.baggageservice.BaggageServiceApplication;
import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.service.BaggageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baggages")
public class BaggageController {

    @Autowired
    private BaggageService baggageService;

    @GetMapping("")
    public ResponseEntity<List<BaggageServiceApplication>> getAllBaggage() {
        return ResponseEntity.ok(baggageService.getAllBaggage());
    }

    @PostMapping("/reserve")
    public ResponseEntity<BaggageResponseDto> reserveBaggage(@RequestBody BaggageRequestDto requestDto) {
        BaggageResponseDto response = baggageService.reserveBaggage(requestDto);
        return ResponseEntity.ok(response);


    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BaggageResponseDto>> getBaggageByBooking(@PathVariable Long bookingId) {
        List<BaggageResponseDto> baggages = baggageService.getBaggagesByBookingId(bookingId);
        return ResponseEntity.ok(baggages);
    }

    @PostMapping("/{baggageId}/checkin")
    public ResponseEntity<BaggageResponseDto> checkInBaggage(@PathVariable Long baggageId) {
        BaggageResponseDto checkedIn = baggageService.checkInBaggage(baggageId);
        return ResponseEntity.ok(checkedIn);
    }
}
