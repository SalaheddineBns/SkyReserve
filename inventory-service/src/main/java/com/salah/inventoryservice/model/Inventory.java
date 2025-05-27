package com.salah.inventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente l'inventaire des sièges disponibles pour un vol donné.
 *
 * Chaque Inventory est lié à un vol spécifique (via flightId) et gère le nombre
 * total de sièges encore disponibles (nbrOfAvailableSeats).
 *
 * La gestion des sièges spécifiques (numéros, classes, etc.) se fait dans une entité distincte (Seat).
 */
@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(nullable = false)
    private Long flightId;

    private int nbrOfAvailableSeats;

    public Inventory(Long flightId, int totalSeats) {
        this.flightId = flightId;
        this.nbrOfAvailableSeats = totalSeats;
    }

    /**
     * Réserve un nombre de sièges spécifié si des places sont disponibles.
     *
     * @param seatsRequested Nombre de sièges à réserver
     * @return true si la réservation est acceptée, false sinon
     */
    public boolean reserveSeats(int seatsRequested) {
        if (nbrOfAvailableSeats >= seatsRequested) {
            nbrOfAvailableSeats -= seatsRequested;
            return true;
        }
        return false;
    }

    /**
     * Libère un nombre de sièges spécifié (par exemple en cas d'annulation).
     *
     * @param seatsReleased Nombre de sièges à libérer
     */
    public void releaseSeats(int seatsReleased) {
        nbrOfAvailableSeats += seatsReleased;
    }
}
