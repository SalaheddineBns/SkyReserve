package com.salah.checkinservice.controller;

import com.salah.checkinservice.dto.*;
import com.salah.checkinservice.service.CheckinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/checkin")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;

    @PostMapping("/random")
    public ResponseEntity<CheckinResponseDto> checkinRandom(@RequestBody CheckinRequestDto dto) {
        return ResponseEntity.ok(checkinService.performCheckin(dto.getBookingId(), null, dto.getPassengerId()));
    }

    @PostMapping
    public ResponseEntity<CheckinResponseDto> checkinWithSeat(@RequestBody CheckinRequestDto dto) {
        return ResponseEntity.ok(checkinService.performCheckin(dto.getBookingId(), dto.getSeatNumber(),dto.getPassengerId()));
    }

    @GetMapping
    public List<CheckinResponseDto> getAllCheckins() {
        return checkinService.getAllCheckins();
    }

    @GetMapping("/available-seats/{flightId}")
    public ResponseEntity<List<String>> getAvailableSeats(@PathVariable Long flightId) {
        return ResponseEntity.ok(checkinService.getAvailableSeats(flightId));
    }

    @GetMapping("/boarding-pass/{bookingId}")
    public ResponseEntity<List<CheckinResponseDto>> getCheckins(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(checkinService.getCheckinsByBookingId(bookingId));
    }


    @GetMapping("/{bookingId}/passengers")
    public ResponseEntity<List<PassengerDto>> getPassengers(@PathVariable UUID bookingId) {
        BookingDto booking = checkinService.getBookingDetails(bookingId);
        return ResponseEntity.ok(booking.getPassengers());
    }

    @GetMapping("/boarding-pass/{boardingPassNumber}")
    public ResponseEntity<BoardingPassDto> getBoardingPass(@PathVariable UUID boardingPassNumber) {
        return ResponseEntity.ok(checkinService.generateBoardingPass(boardingPassNumber));
    }



}