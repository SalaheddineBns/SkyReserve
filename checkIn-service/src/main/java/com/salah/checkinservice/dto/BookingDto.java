package com.salah.checkinservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private UUID bookingId;
    private String flightNumber;
    private Long flightId;
    private String status;
    private List<PassengerDto> passengers;
}