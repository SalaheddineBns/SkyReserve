package com.salah.bookingservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
// status a voir !!! pending , confirmed, canceled


public record BookingResponseDto(
        UUID bookingId,         // Identifiant unique de la réservation
        Long flightId,          // Identifiant du vol réservé
        Integer seats,          // Nombre de sièges réservés
        Double totalPrice,      // Prix total payé ou à payer
        String status,          // PENDING, CONFIRMED, CANCELED

        LocalDateTime bookingDate,    // Date et heure de la réservation
        LocalDateTime expirationDate, // Date et heure d’expiration


        // Infos sur le client
        String firstName,
        String lastName,
        String email,
        String phone,
        String civility,    // M., Mme., etc.

        // 🎯 La liste des passagers liés à cette réservation
        List<PassengerDto>passengers
        // (Optionnel) Si tu veux, tu peux inclure aussi :
        // List<BaggageOptionDto> baggages  // Détails des bagages si besoin
) {}
