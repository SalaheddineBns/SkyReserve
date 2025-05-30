package com.salah.bookingservice.dto;

import com.salah.bookingservice.model.enums.BaggageType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaggageRequestDto {
    private Long bookingId;
    private BaggageType type;
    private Double weight;
    private Double price;
}
