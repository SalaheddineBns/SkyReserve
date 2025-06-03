package com.salah.bookingservice.dto;

import com.salah.bookingservice.model.enums.BaggageStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class BaggageResponseDto {
    private Long id;
    private UUID bookingId;
    private String type;
    private Double weight;
    private Double price;
    private BaggageStatus status;
}
