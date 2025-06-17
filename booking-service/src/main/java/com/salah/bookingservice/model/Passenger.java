package com.salah.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String civilite;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    // + getters, setters, constructeurs
}
