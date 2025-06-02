package com.salah.baggageservice.dto;

import com.salah.baggageservice.model.enums.BaggageType;
import lombok.Data;

import java.util.UUID;

@Data
public class BaggageRequestDto {
    private UUID bookingId;
    private BaggageType type;
    private Double weight;
    private Double price;
}


