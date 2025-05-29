package com.salah.baggageservice.dto;

import com.salah.baggageservice.model.enums.BaggageType;
import lombok.Data;

@Data
public class BaggageRequestDto {
    private Long bookingId;
    private BaggageType type;
    private Double weight;
    private Double price;
}


