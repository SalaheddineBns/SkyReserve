package com.salah.aircraftservice.controller;

import com.salah.aircraftservice.dto.AircraftRequestDTO;
import com.salah.aircraftservice.dto.AircraftResponseDTO;
import com.salah.aircraftservice.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public List<AircraftResponseDTO> getAll() {
        return aircraftService.getAll();
    }

    @GetMapping("/{id}")
    public AircraftResponseDTO getById(@PathVariable Long id) {
        return aircraftService.getById(id);
    }

    @PostMapping
    public AircraftResponseDTO save(@RequestBody AircraftRequestDTO dto) {
        return aircraftService.save(dto);
    }

    // PUT 同理，用 RequestDTO 请求体
    @PutMapping("/{id}")
    public AircraftResponseDTO update(@PathVariable Long id, @RequestBody AircraftRequestDTO dto) {
        return aircraftService.updateAircraft(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        aircraftService.delete(id);
    }
}