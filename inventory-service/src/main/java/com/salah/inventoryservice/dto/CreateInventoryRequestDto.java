package com.salah.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateInventoryRequestDto {
    private Long flightId;
    private int totalSeats;
}

