package com.salah.aircraftservice.service;

import com.salah.aircraftservice.dto.AircraftRequestDTO;
import com.salah.aircraftservice.dto.AircraftResponseDTO;

import java.util.List;

public interface AircraftService {
    AircraftResponseDTO save(AircraftRequestDTO dto);
    List<AircraftResponseDTO> getAll();
    AircraftResponseDTO getById(Long id);
    AircraftResponseDTO updateAircraft(Long id, AircraftRequestDTO updatedAircraft);
    void delete(Long id);
}