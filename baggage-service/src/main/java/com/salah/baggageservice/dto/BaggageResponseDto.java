package com.salah.baggageservice.dto;

import com.salah.baggageservice.model.enums.BaggageStatus;
import com.salah.baggageservice.model.enums.BaggageType;
import lombok.Data;

@Data
public class BaggageResponseDto {
    private Long id;
    private Long bookingId;
    private BaggageType type;
    private Double weight;
    private Double price;
    private BaggageStatus status;
}
