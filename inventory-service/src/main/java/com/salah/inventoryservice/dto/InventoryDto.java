package com.salah.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InventoryDto {
    private Long flightId;
    private LocalDate flightDate;
    private int totalSeats;
    private int availableSeats;
}
