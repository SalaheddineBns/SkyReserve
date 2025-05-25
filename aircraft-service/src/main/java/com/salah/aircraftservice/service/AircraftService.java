package com.salah.aircraftservice.service;

import com.salah.aircraftservice.dto.AircraftDTO;

import java.util.List;

public interface AircraftService {
    AircraftDTO save(AircraftDTO dto);
    List<AircraftDTO> getAll();
    AircraftDTO getById(Long id);
    AircraftDTO updateAircraft(Long id, AircraftDTO updatedAircraft);
    void delete(Long id);
}