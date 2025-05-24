package com.salah.inventoryservice.service;

import com.salah.inventoryservice.dto.InventoryDto;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Inventory createInventory(InventoryDto request) {
        Inventory inventory = new Inventory();
        inventory.setFlightId(request.getFlightId());
        inventory.setFlightDate(request.getFlightDate());
        inventory.setTotalSeats(request.getTotalSeats());
        inventory.setAvailableSeats(request.getAvailableSeats());
       // inventory.setLastUpdated(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    public boolean checkAvailability(Long flightId, LocalDate flightDate, int seatsRequested) {
        return inventoryRepository.findByFlightIdAndFlightDate(flightId, flightDate)
                .map(inventory -> inventory.getAvailableSeats() >= seatsRequested)
                .orElse(false);
    }
    public boolean reserveSeats(Long flightId, LocalDate flightDate, int seatsRequested) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByFlightIdAndFlightDate(flightId, flightDate);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            if (inventory.reserveSeats(seatsRequested)) {
                inventoryRepository.save(inventory);
                return true;
            }
        }
        return false;
    }

    public void releaseSeats(Long flightId, LocalDate date, int seats) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByFlightIdAndFlightDate(flightId, date);
        inventoryOpt.ifPresent(inv -> {
            inv.releaseSeats(seats);
            inventoryRepository.save(inv);
        });
    }

}
