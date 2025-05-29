package com.salah.baggageservice.model;

import com.salah.baggageservice.model.enums.BaggageStatus;
import com.salah.baggageservice.model.enums.BaggageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "baggages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Baggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    @Enumerated(EnumType.STRING)
    private BaggageType type;

    private Double weight;

    private Double price;

    @Enumerated(EnumType.STRING)
    private BaggageStatus status;  // e.g. RESERVED, CHECKED_IN, etc.
}
