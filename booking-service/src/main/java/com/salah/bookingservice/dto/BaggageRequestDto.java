package com.salah.bookingservice.dto;

import com.salah.bookingservice.model.enums.BaggageType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BaggageRequestDto {
    private UUID bookingId;
    private Long passengerId;
    private BaggageType type;
    private Double weight;
    private Double price;
}
