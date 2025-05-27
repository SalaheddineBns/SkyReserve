package com.salah.inventoryservice.controller;

import com.salah.inventoryservice.dto.InventoryRequestDto;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer l'inventaire des sièges d'un vol.
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * Crée un nouvel inventaire pour un vol donné.
     *
     * @param request DTO contenant flightId et totalSeats
     * @return L'inventaire créé
     */
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody InventoryRequestDto request) {
        Inventory created = inventoryService.createInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Récupère tous les inventaires existants.
     *
     * @return Liste des inventaires
     */
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    /**
     * Vérifie si un nombre de sièges demandé est disponible pour un vol donné.
     *
     * @param flightId       Identifiant du vol
     * @param seatsRequested Nombre de sièges demandés
     * @return true si disponible, false sinon
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long flightId,
            @RequestParam int seatsRequested
    ) {
        boolean available = inventoryService.checkAvailability(flightId, seatsRequested);
        return ResponseEntity.ok(available);
    }

    /**
     * Récupère le nombre de sièges disponibles pour un vol donné.
     *
     * @param flightId Identifiant du vol
     * @return Nombre de sièges disponibles
     */
    @GetMapping("/available-seats")
    public ResponseEntity<Integer> getAvailableSeats(
            @RequestParam Long flightId
    ) {
        Integer availableSeats = inventoryService.getAvailableSeats(flightId);
        return ResponseEntity.ok(availableSeats);
    }

    /**
     * Réserve un nombre de sièges pour un vol donné.
     *
     * @param flightId       Identifiant du vol
     * @param seatsRequested Nombre de sièges à réserver
     * @return Message de confirmation ou d'erreur
     */
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveSeats(
            @RequestParam Long flightId,
            @RequestParam int seatsRequested
    ) {
        boolean reserved = inventoryService.reserveSeats(flightId, seatsRequested);
        return reserved ?
                ResponseEntity.ok("Seats reserved") :
                ResponseEntity.badRequest().body("Not enough seats available");
    }

    /**
     * Libère un nombre de sièges pour un vol donné.
     *
     * @param flightId Identifiant du vol
     * @param seats    Nombre de sièges à libérer
     * @return Message de confirmation
     */
    @PostMapping("/release")
    public ResponseEntity<String> releaseSeats(
            @RequestParam Long flightId,
            @RequestParam int seats
    ) {
        inventoryService.releaseSeats(flightId, seats);
        return ResponseEntity.ok("Seats released");
    }
}
