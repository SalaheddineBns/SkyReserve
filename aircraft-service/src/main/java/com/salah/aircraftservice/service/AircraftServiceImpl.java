package com.salah.aircraftservice.service;

import com.salah.aircraftservice.dto.AircraftRequestDTO;
import com.salah.aircraftservice.dto.AircraftResponseDTO;
import com.salah.aircraftservice.mapper.AircraftMapper;
import com.salah.aircraftservice.model.Aircraft;
import com.salah.aircraftservice.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    private AircraftRepository repository;

    // seatClassRowsMap 用于暂存 seatClassRows
    private final Map<Long, Map<String, List<Integer>>> seatClassRowsMap = new HashMap<>();

    @Override
    public AircraftResponseDTO save(AircraftRequestDTO dto) {
        Aircraft entity = AircraftMapper.toEntity(dto);

        List<String> seatLayout = generateSeatLayout(dto.getNumberOfRows(), dto.getSeatPerRow());
        entity.setSeatLayout(seatLayout);
        entity.setCapacity(seatLayout.size());

        Aircraft saved = repository.save(entity);

        // 保存 seatClassRows
        seatClassRowsMap.put(saved.getId(), dto.getSeatClassRows());

        return AircraftMapper.toDto(saved, dto.getSeatClassRows());
    }

    @Override
    public List<AircraftResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(aircraft -> {
                    Map<String, List<Integer>> seatClassRows = seatClassRowsMap.getOrDefault(aircraft.getId(), new HashMap<>());
                    return AircraftMapper.toDto(aircraft, seatClassRows);
                })
                .toList();
    }

    @Override
    public AircraftResponseDTO getById(Long id) {
        Aircraft aircraft = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        Map<String, List<Integer>> seatClassRows = seatClassRowsMap.getOrDefault(id, new HashMap<>());

        return AircraftMapper.toDto(aircraft, seatClassRows);
    }

    @Override
    public AircraftResponseDTO updateAircraft(Long id, AircraftRequestDTO dto) {
        Aircraft existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));

        existing.setCode(dto.getCode());
        existing.setType(dto.getType());
        existing.setAirline(dto.getAirline());
        existing.setStatus(dto.getStatus());
        existing.setNumberOfRows(dto.getNumberOfRows());
        existing.setSeatPerRow(dto.getSeatPerRow());

        List<String> seatLayout = generateSeatLayout(dto.getNumberOfRows(), dto.getSeatPerRow());
        existing.setSeatLayout(seatLayout);
        existing.setCapacity(seatLayout.size());

        Aircraft saved = repository.save(existing);

        // 更新 seatClassRows
        seatClassRowsMap.put(saved.getId(), dto.getSeatClassRows());

        return AircraftMapper.toDto(saved, dto.getSeatClassRows());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        seatClassRowsMap.remove(id);
    }

    private List<String> generateSeatLayout(int numberOfRows, String seatPerRow) {
        List<String> seatLayout = new ArrayList<>();
        for (int row = 1; row <= numberOfRows; row++) {
            for (char seat : seatPerRow.toCharArray()) {
                seatLayout.add(row + String.valueOf(seat));
            }
        }
        return seatLayout;
    }
}
