package com.salah.bookingservice.model;

import com.salah.bookingservice.model.enums.BaggageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BaggageReservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaggageReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baggageReservationId;
    private Long bookingId;
    private BaggageType type;
    private int quantity;
}
