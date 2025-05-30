package com.salah.flightservice.dto;

import lombok.Data;

@Data
public class AircraftDto {
    private Long id;
    private String code;
    private String type;
    private int capacity;
    private String airline;
    private String status;
}
