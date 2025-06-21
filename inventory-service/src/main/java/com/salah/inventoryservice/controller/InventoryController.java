package com.salah.inventoryservice.controller;

import com.salah.inventoryservice.dto.*;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.model.Seat;
import com.salah.inventoryservice.repository.InventoryRepository;
import com.salah.inventoryservice.repository.SeatRepository;
import com.salah.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/inventory")

public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody CreateInventoryRequestDto request) {
        return ResponseEntity.ok(inventoryService.createInventory(request));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }


    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long flightId, @RequestParam int seatsRequested) {
        boolean available = inventoryService.isSeatAvailable(flightId, seatsRequested);
        return ResponseEntity.ok(available);
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserveSeats(@RequestBody SeatReservationRequestDto request) {
        inventoryService.reserveSeats(request.flightId(), request.seats());
        return ResponseEntity.ok().build();
    }



    @GetMapping("/seats-number/{flightId}")
    public ResponseEntity<List<String>> getAvailableSeats(@PathVariable Long flightId) {
        return ResponseEntity.ok(inventoryService.getAvailableSeats(flightId));
    }

    @PutMapping("/seats/assign")
    public ResponseEntity<Void> assignSeatToPassenger(@RequestBody SeatAssignmentRequestDto request) {
        inventoryService.assignSeatToPassenger(request);
        return ResponseEntity.ok().build();
    }


}
