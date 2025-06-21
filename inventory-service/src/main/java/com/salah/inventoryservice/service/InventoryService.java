package com.salah.inventoryservice.service;

import com.salah.inventoryservice.dto.CreateInventoryRequestDto;
import com.salah.inventoryservice.dto.SeatAssignmentRequestDto;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.model.Seat;
import com.salah.inventoryservice.repository.InventoryRepository;
import com.salah.inventoryservice.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private  InventoryRepository inventoryRepository;
    @Autowired
    private  SeatRepository seatRepository;




    @Transactional
    public Inventory createInventory(CreateInventoryRequestDto request) {
        Inventory inventory = new Inventory(request.getFlightId(), request.getTotalSeats());

        // 1. Générer les sièges AVANT toute sauvegarde
        List<Seat> seats = generateSeats(inventory, request.getTotalSeats());

        // 2. Lier les sièges à l'inventaire (inversement aussi)
        inventory.setSeats(seats);

        // 3. Enregistrer l’inventaire (cascade va enregistrer les seats automatiquement si configuré)
        return inventoryRepository.save(inventory);
    }



    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public List<String> getAvailableSeats(Long flightId) {
        return seatRepository.findByInventory_FlightIdAndIsAvailableTrue(flightId)
                .stream().map(Seat::getSeatNumber).collect(Collectors.toList());
    }

    public void assignSeatToPassenger(SeatAssignmentRequestDto request) {
        Seat seat = seatRepository.findBySeatNumberAndInventory_FlightId(request.getSeatNumber(), request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (!seat.isAvailable()) {
            throw new IllegalStateException("Seat already assigned.");
        }

        seat.setAvailable(false);
        seat.setPassengerId(request.getPassengerId());
        seatRepository.save(seat);
    }

    private List<Seat> generateSeats(Inventory inventory, int totalSeats) {
        List<Seat> seats = new ArrayList<>();
        int rows = totalSeats / 6;
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F'};

        for (int row = 1; row <= rows; row++) {
            for (char col : columns) {
                if (seats.size() >= totalSeats) break;
                seats.add(Seat.builder()
                        .seatNumber(row + "" + col)
                        .inventory(inventory)
                        .isAvailable(true)
                        .build());
            }
        }
        return seats;
    }

    public boolean isSeatAvailable(Long flightId, int seatsRequested) {
        Inventory inventory = inventoryRepository.findByFlightId(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));

        return inventory.getNbrOfAvailableSeats() >= seatsRequested;
    }

    public void reserveSeats(Long flightId, int seatsRequested) {
        Inventory inventory = inventoryRepository.findByFlightId(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));

        if (inventory.getNbrOfAvailableSeats() < seatsRequested) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough seats available");
        }

        inventory.setNbrOfAvailableSeats(inventory.getNbrOfAvailableSeats() - seatsRequested);
        inventoryRepository.save(inventory);
    }

}
