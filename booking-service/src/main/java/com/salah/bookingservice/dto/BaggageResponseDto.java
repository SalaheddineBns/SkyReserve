package com.salah.bookingservice.dto;

import com.salah.bookingservice.model.enums.BaggageStatus;
import lombok.Data;

@Data
public class BaggageResponseDto {
    private Long id;
    private Long bookingId;
    private String type;
    private Double weight;
    private Double price;
    private BaggageStatus status;
}
