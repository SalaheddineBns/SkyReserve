package com.salah.inventoryservice.controller;

import com.salah.inventoryservice.dto.InventoryDto;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryDto request) {
        Inventory created = inventoryService.createInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }



    @GetMapping
    public ResponseEntity<List<Inventory>> getInventory(){
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long flightId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int seats
    ) {
        boolean available = inventoryService.checkAvailability(flightId, date, seats);
        return ResponseEntity.ok(available);
    }
    @GetMapping("available-seats")
    public Integer getAvailableSeats(
            @RequestParam Long flightId
    ) {
        return inventoryService.getAvailableSeats(flightId);
    }


    @PostMapping("/reserve")
    public ResponseEntity<String> reserveSeats(@RequestParam Long flightId,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                               @RequestParam int seats) {
        boolean reserved = inventoryService.reserveSeats(flightId, date, seats);
        return reserved ? ResponseEntity.ok("Seats reserved") : ResponseEntity.badRequest().body("Not enough seats");
    }

    @PostMapping("/release")
    public ResponseEntity<String> releaseSeats(@RequestParam Long flightId,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                               @RequestParam int seats) {
        inventoryService.releaseSeats(flightId, date, seats);
        return ResponseEntity.ok("Seats released");
    }
}
