package com.salah.bookingservice.dto;

import java.time.LocalDateTime;
// status a voir !!! pending , confirmed, canceled


public record BookingResponseDto(
        Long bookingId,         // Identifiant unique de la réservation
        Long flightId,          // Identifiant du vol réservé
        Integer seats,          // Nombre de sièges réservés
        Double totalPrice,      // Prix total payé ou à payer
        String status,          // PENDING, CONFIRMED, CANCELED

        LocalDateTime bookingDate,    // Date et heure de la réservation
        LocalDateTime expirationDate, // Date et heure d’expiration

        Double baggagesFees,    // Frais de bagages (si applicable)

        // Infos sur le client
        String firstName,
        String lastName,
        String email,
        String phone,
        String civility          // M., Mme., etc.

        // (Optionnel) Si tu veux, tu peux inclure aussi :
        // List<BaggageOptionDto> baggages  // Détails des bagages si besoin
) {}
