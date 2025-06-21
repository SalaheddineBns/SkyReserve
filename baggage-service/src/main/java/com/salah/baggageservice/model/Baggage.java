package com.salah.baggageservice.model;

import com.salah.baggageservice.model.enums.BaggageStatus;
import com.salah.baggageservice.model.enums.BaggageType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "baggages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Baggage {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private UUID bookingId;

    private Long passengerId;

    @Enumerated(EnumType.STRING)
    private BaggageType type;

    private Double weight;

    private Double price;

    @Enumerated(EnumType.STRING)
    private BaggageStatus status;
}
