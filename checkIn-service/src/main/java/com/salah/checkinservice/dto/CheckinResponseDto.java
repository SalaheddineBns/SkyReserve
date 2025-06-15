package com.salah.checkinservice.dto;


import com.salah.checkinservice.entity.Checkin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinResponseDto {
    private UUID checkinId;
    private UUID bookingId;
    private String seatNumber;
    private UUID boardingPassNumber;
    private LocalDateTime checkinTime;
    private String firstName;
    private String lastName;

    public CheckinResponseDto(Checkin entity) {
        this.checkinId = entity.getId();
        this.bookingId = entity.getBookingId();
        this.seatNumber = entity.getSeatNumber();
        this.boardingPassNumber = entity.getBoardingPassNumber();
        this.checkinTime = entity.getCheckinTime();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
    }
}