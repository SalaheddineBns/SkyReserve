package com.salah.checkinservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Checkin {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID bookingId;

    private Long passengerId;
    private String seatNumber;

    private UUID boardingPassNumber;

    private LocalDateTime checkinTime;

    private String firstName;

    private String lastName;
}