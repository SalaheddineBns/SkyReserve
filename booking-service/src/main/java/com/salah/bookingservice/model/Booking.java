package com.salah.bookingservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


// status a voir !!! pending , confirmed, canceled
@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;          // Qui a réservé (récupéré via JWT ou param)
    private Long flightId;        // Le vol réservé

    private Integer seats;        // Nombre de sièges réservés
    private Double totalPrice;    // Total payé ou à payer

    private String status;        // PENDING, CONFIRMED, CANCELED

    private LocalDateTime bookingDate;  // Date et heure de réservation

    private String paymentId;     // Référence au paiement (si intégré plus tard)
}
