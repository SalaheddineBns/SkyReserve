package com.salah.bookingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private UUID bookingId;

    private Long flightId;
    private Integer seats;            // Nombre de sièges réservés
    private Double totalPrice;        // Total payé ou à payer
    private String status;            // PENDING, CONFIRMED, CANCELED
    private LocalDateTime bookingDate;// Date et heure de réservation
    private Double baggagesFees;
    private LocalDateTime expirationDate;

    // Informations utilisateur associées à la réservation
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String civility;          // M. / Mme. / etc.
}
