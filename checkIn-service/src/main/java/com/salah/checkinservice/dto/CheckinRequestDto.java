package com.salah.checkinservice.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinRequestDto {
    private UUID bookingId;
    private String seatNumber;
    private String firstName;
    private String lastName;
}
