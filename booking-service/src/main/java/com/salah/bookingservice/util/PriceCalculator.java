package com.salah.bookingservice.util;


import com.salah.bookingservice.dto.BaggageOption;
import com.salah.bookingservice.dto.FlightDto;
import com.salah.bookingservice.model.enums.BaggageType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utilitaire pour calculer le prix total d'une réservation.
 * Calcule le prix des sièges et des bagages en fonction des options choisies.
 */
@Component
public class PriceCalculator {

    /**
     * Calcule le prix total d'une réservation.
     *
     * @param flight      Le vol choisi (FlightDto)
     * @param seats       Nombre de sièges réservés
     * @param baggages    Options de bagage choisies (liste BaggageOption)
     * @return Le prix total de la réservation
     */
    public double calculateTotalPrice(FlightDto flight, int seats, List<BaggageOption> baggages) {
        double seatPrice = calculateSeatPrice(flight.price(), seats);
        double baggageFees = calculateBaggageFees(baggages);
        return seatPrice + baggageFees;
    }

    /**
     * Calcule le prix des sièges.
     *
     * @param seatPrice Prix unitaire d'un siège (récupéré du FlightDto)
     * @param seats     Nombre de sièges réservés
     * @return Le prix total des sièges
     */
    private double calculateSeatPrice(double seatPrice, int seats) {
        return seatPrice * seats;
    }

    /**
     * Calcule le prix total des bagages en fonction des options choisies.
     *
     * @param baggages Liste des options de bagage choisies
     * @return Le total des frais de bagages
     */
    public double calculateBaggageFees(List<BaggageOption> baggages) {
        if (baggages == null || baggages.isEmpty()) {
            return 0.0;
        }

        return baggages.stream()
                .mapToDouble(this::getBaggagePrice)
                .sum();
    }

    /**
     * Calcule le prix d'une option de bagage (par type et quantité).
     *
     * @param baggageOption L'option de bagage choisie
     * @return Le prix total pour cette option
     */
    private double getBaggagePrice(BaggageOption baggageOption) {
        double unitPrice;

        // Tarification fixe par type de bagage (exemple)
        switch (baggageOption.type()) {
            case SOUTE -> unitPrice = 30.0;
            case CABIN -> unitPrice = 0.0;
            default -> unitPrice = 0.0;
        }

        return unitPrice * baggageOption.quantity();
    }
}
