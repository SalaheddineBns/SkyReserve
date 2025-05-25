package com.salah.aircraftservice.service;

import com.salah.aircraftservice.dto.AircraftDTO;
import com.salah.aircraftservice.mapper.AircraftMapper;
import com.salah.aircraftservice.model.Aircraft;
import com.salah.aircraftservice.repository.AircraftRepository;
import com.salah.aircraftservice.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    private AircraftRepository repository;

    @Override
    public AircraftDTO save(AircraftDTO dto) {
        Aircraft entity = AircraftMapper.toEntity(dto);
        Aircraft saved = repository.save(entity);
        return AircraftMapper.toDto(saved);
    }

    @Override
    public List<AircraftDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(AircraftMapper::toDto)
                .toList();
    }

    @Override
    public AircraftDTO getById(Long id) {
        Aircraft aircraft = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
        return AircraftMapper.toDto(aircraft);
    }

    @Override
    public AircraftDTO updateAircraft(Long id, AircraftDTO updatedDTO) {
        Aircraft existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        // 更新字段（按需更新）
        existing.setCode(updatedDTO.getCode());
        existing.setType(updatedDTO.getType());
        existing.setCapacity(updatedDTO.getCapacity());
        existing.setAirline(updatedDTO.getAirline());
        existing.setStatus(updatedDTO.getStatus());

        Aircraft saved = repository.save(existing);
        return AircraftMapper.toDto(saved);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}