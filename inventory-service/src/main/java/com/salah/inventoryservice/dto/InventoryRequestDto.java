package com.salah.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryRequestDto {
    private Long flightId;
    private int totalSeats;
}
