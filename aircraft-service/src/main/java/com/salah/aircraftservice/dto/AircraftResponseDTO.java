package com.salah.aircraftservice.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponseDTO {
    private Long id;
    private String code;
    private String type;
    private int capacity;
    private String airline;
    private String status;

    private int numberOfRows;
    private String seatPerRow;
    private List<String> seatLayout;

    private Map<String, List<Integer>> seatClassRows; // key = "FIRST", "ECONOMY", value = list of rows
}
