package com.salah.inventoryservice.service;

import com.salah.inventoryservice.client.FlightClient;
import com.salah.inventoryservice.dto.FlightDto;
import com.salah.inventoryservice.dto.InventoryRequestDto;
import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.model.Seat;
import com.salah.inventoryservice.repository.InventoryRepository;
import com.salah.inventoryservice.repository.SeatRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer l'inventaire des sièges d'un vol donné.
 * Gère la création, la récupération, et la réservation/libération des sièges.
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatService seatService;
    @Autowired
    private FlightClient flightClient;
    /**
     * Crée un nouvel inventaire pour un vol donné avec le nombre total de sièges.
     *
     * @param request DTO contenant flightId et le nombre total de sièges
     * @return L'inventaire sauvegardé
     */

    public Inventory createInventory(InventoryRequestDto request) {
        // Vérifier que le flightId existe
        try {
            FlightDto flight = flightClient.getFlightById(request.getFlightId());
            if (flight == null) {
                throw new IllegalArgumentException("Flight not found in FlightService");
            }
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Flight not found in FlightService");
        }

        Inventory inventory = new Inventory();
        inventory.setFlightId(request.getFlightId());
        inventory.setNbrOfAvailableSeats(request.getTotalSeats());

        Inventory savedInventory = inventoryRepository.save(inventory);
        List<Seat> seats = seatService.generateSeats(savedInventory.getInventoryId(), request.getTotalSeats());
        seatRepository.saveAll(seats);

        return savedInventory;
    }



    /**
     * Récupère tous les inventaires.
     *
     * @return Liste des inventaires existants
     */
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * Récupère le nombre de sièges disponibles pour un vol donné.
     *
     * @param flightId Identifiant du vol
     * @return Nombre de sièges disponibles, ou 0 si non trouvé
     */
    public Integer getAvailableSeats(Long flightId) {
        return inventoryRepository.findByFlightId(flightId)
                .map(Inventory::getNbrOfAvailableSeats)
                .orElse(0);
    }

    /**
     * Vérifie si un nombre de sièges demandé est disponible pour un vol donné.
     *
     * @param flightId       Identifiant du vol
     * @param flightDate     (Supprimé du modèle, à retirer si non utilisé)
     * @param seatsRequested Nombre de sièges demandés
     * @return true si disponible, false sinon
     */
    public boolean checkAvailability(Long flightId, int seatsRequested) {
        return inventoryRepository.findByFlightId(flightId)
                .map(inventory -> inventory.getNbrOfAvailableSeats() >= seatsRequested)
                .orElse(false);
    }

    /**
     * Réserve un nombre de sièges pour un vol donné, si disponible.
     *
     * @param flightId       Identifiant du vol
     * @param seatsRequested Nombre de sièges demandés
     * @return true si la réservation est réussie, false sinon
     */
    public boolean reserveSeats(Long flightId, int seatsRequested) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByFlightId(flightId);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            if (inventory.reserveSeats(seatsRequested)) {
                inventoryRepository.save(inventory);
                return true;
            }
        }
        return false;
    }

    /**
     * Libère un nombre de sièges précédemment réservés.
     *
     * @param flightId Identifiant du vol
     * @param seats    Nombre de sièges à libérer
     */
    public void releaseSeats(Long flightId, int seats) {
        inventoryRepository.findByFlightId(flightId).ifPresent(inventory -> {
            inventory.releaseSeats(seats);
            inventoryRepository.save(inventory);
        });
    }
}
