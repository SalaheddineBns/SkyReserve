package com.salah.inventoryservice;


import com.salah.inventoryservice.client.FlightClient;
import com.salah.inventoryservice.dto.CreateInventoryRequestDto;
import com.salah.inventoryservice.dto.FlightDto;
import com.salah.inventoryservice.service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryInitializer implements CommandLineRunner {

    private final InventoryService inventoryService;
    private final FlightClient flightClient;

    public InventoryInitializer(InventoryService inventoryService, FlightClient flightClient) {
        this.inventoryService = inventoryService;
        this.flightClient = flightClient;
    }

    @Override
    public void run(String... args) {
        try {
            List<FlightDto> flights = flightClient.getFlights(); // méthode à ajouter dans FlightClient

            for (FlightDto flight : flights) {
               CreateInventoryRequestDto createInventoryRequestDto= new CreateInventoryRequestDto(flight.getFlightId(), 200);
                inventoryService.createInventory(createInventoryRequestDto);
                System.out.println("✅ Inventaire créé pour le vol ID: " + flight.getFlightId());
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur pendant l'initialisation des inventaires: " + e.getMessage());
        }
    }
}
