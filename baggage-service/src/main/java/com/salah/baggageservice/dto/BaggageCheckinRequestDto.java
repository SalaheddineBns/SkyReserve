package com.salah.baggageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaggageCheckinRequestDto {
    private UUID bookingId;
    private Long passengerId;
}
