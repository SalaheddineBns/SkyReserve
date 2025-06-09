package com.salah.aircraftservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code; // ex: A320, B737

    private String type; // court, moyen, long courrier

    private int capacity;

    private String airline;

    private String status; // actif, maintenance, etc.

    private int numberOfRows;

    private String seatPerRow;

    @ElementCollection
    @CollectionTable(name = "aircraft_seat_layout", joinColumns = @JoinColumn(name = "aircraft_id"))
    @Column(name = "seat_number")
    private List<String> seatLayout;
}

