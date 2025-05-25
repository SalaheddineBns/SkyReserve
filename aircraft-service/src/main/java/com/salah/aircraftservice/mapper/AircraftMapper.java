package com.salah.aircraftservice.mapper;

import com.salah.aircraftservice.dto.AircraftDTO;
import com.salah.aircraftservice.model.Aircraft;

public class AircraftMapper {

    public static AircraftDTO toDto(Aircraft aircraft) {
        if (aircraft == null) return null;

        return AircraftDTO.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .type(aircraft.getType())
                .capacity(aircraft.getCapacity())
                .airline(aircraft.getAirline())
                .status(aircraft.getStatus())
                .build();
    }

    public static Aircraft toEntity(AircraftDTO dto) {
        if (dto == null) return null;

        return Aircraft.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .type(dto.getType())
                .capacity(dto.getCapacity())
                .airline(dto.getAirline())
                .status(dto.getStatus())
                .build();
    }
}