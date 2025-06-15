package com.salah.checkinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardingPassDto {
    private String passengerName;
    private String flightNumber;
    private String seatNumber;
    private LocalDateTime flightTime;
    private String gate;
    private UUID boardingPassNumber;
}
