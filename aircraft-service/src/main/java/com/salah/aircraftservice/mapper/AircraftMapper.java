package com.salah.aircraftservice.mapper;

import com.salah.aircraftservice.dto.AircraftRequestDTO;
import com.salah.aircraftservice.dto.AircraftResponseDTO;
import com.salah.aircraftservice.model.Aircraft;

import java.util.List;
import java.util.Map;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequestDTO dto) {
        if (dto == null) return null;

        return Aircraft.builder()
                .code(dto.getCode())
                .type(dto.getType())
                .airline(dto.getAirline())
                .status(dto.getStatus())
                .numberOfRows(dto.getNumberOfRows())
                .seatPerRow(dto.getSeatPerRow())
                // seatLayout 会在 save() 自动生成！
                .build();
    }

    // 这里 seatClassRows 从 Service 传进来
    public static AircraftResponseDTO toDto(Aircraft aircraft, Map<String, List<Integer>> seatClassRows) {
        if (aircraft == null) return null;

        return AircraftResponseDTO.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .type(aircraft.getType())
                .capacity(aircraft.getCapacity())
                .airline(aircraft.getAirline())
                .status(aircraft.getStatus())
                .numberOfRows(aircraft.getNumberOfRows())
                .seatPerRow(aircraft.getSeatPerRow())
                .seatLayout(aircraft.getSeatLayout())
                .seatClassRows(seatClassRows) // 必须加这个！
                .build();
    }

}
