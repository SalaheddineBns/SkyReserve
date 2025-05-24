package com.salah.inventoryservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long flightId;
//Un même vol (par exemple AF123) peut exister tous les jours (vol régulier).
//👉 Donc flightId identifie le vol, mais flightDate précise pour quel jour on veut vérifier ou gérer les sièges.
    private LocalDate flightDate;

    private int totalSeats;

    private int availableSeats;

    //private LocalDateTime lastUpdated;

    public Inventory(Long flightId, LocalDate flightDate, int totalSeats) {
        this.flightId = flightId;
        this.flightDate = flightDate;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        //this.lastUpdated = LocalDateTime.now();
    }


    // Logic to reserve or release seats
    public boolean reserveSeats(int seatsRequested) {
        if (availableSeats >= seatsRequested) {
            availableSeats -= seatsRequested;
            //lastUpdated = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public void releaseSeats(int seatsReleased) {
        availableSeats += seatsReleased;
        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
        //lastUpdated = LocalDateTime.now();
    }
}
