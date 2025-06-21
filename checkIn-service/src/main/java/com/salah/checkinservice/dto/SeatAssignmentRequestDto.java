package com.salah.checkinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAssignmentRequestDto {
    private Long flightId;
    private String seatNumber;
    private Long passengerId;
}
