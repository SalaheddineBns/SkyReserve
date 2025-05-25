package com.salah.aircraftservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftDTO {
    private Long id;
    private String code;
    private String type;
    private int capacity;
    private String airline;
    private String status;
}
