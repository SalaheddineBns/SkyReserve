package com.salah.aircraftservice.controller;

import com.salah.aircraftservice.dto.AircraftDTO;
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
    public List<AircraftDTO> getAll() {
        return aircraftService.getAll();
    }

    @GetMapping("/{id}")
    public AircraftDTO getById(@PathVariable Long id) {
        return aircraftService.getById(id);
    }

    @PostMapping
    public AircraftDTO save(@RequestBody AircraftDTO dto) {
        return aircraftService.save(dto);
    }

    @PutMapping("/{id}")
    public AircraftDTO update(@PathVariable Long id, @RequestBody AircraftDTO dto) {
        return aircraftService.updateAircraft(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        aircraftService.delete(id);
    }
}