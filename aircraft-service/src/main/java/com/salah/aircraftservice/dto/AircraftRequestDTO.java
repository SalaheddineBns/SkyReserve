package com.salah.aircraftservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequestDTO {
    private String code;
    private String type;
    private String airline;
    private String status;
    private int numberOfRows;
    private String seatPerRow;
    private Map<String, List<Integer>> seatClassRows;
}
