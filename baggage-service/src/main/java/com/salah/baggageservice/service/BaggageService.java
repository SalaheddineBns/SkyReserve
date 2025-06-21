package com.salah.baggageservice.service;

import com.salah.baggageservice.dto.BaggageCheckinRequestDto;
import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.mapper.BaggageMapper;
import com.salah.baggageservice.model.Baggage;
import com.salah.baggageservice.model.enums.BaggageStatus;
import com.salah.baggageservice.model.enums.BaggageType;
import com.salah.baggageservice.repository.BaggageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BaggageService {
    @Autowired
    private BaggageRepository baggageRepository;
    @Autowired
    private BaggageMapper baggageMapper;

    public List<BaggageResponseDto> getAllBaggage() {
        return baggageRepository.findAll().stream()
                .map(baggageMapper::toDto)
                .collect(Collectors.toList());
    }

    public BaggageResponseDto reserveBaggage(BaggageRequestDto requestDto) {
        BaggageType type = requestDto.getType(); // Assure-toi que getType() retourne bien un BaggageType

        // Vérification
        if (type == null) {
            throw new IllegalArgumentException("Baggage type is required");
        }

        // Création du bagage avec poids et prix par défaut
        Baggage baggage = Baggage.builder()
                .bookingId(requestDto.getBookingId())
                .type(type)
                .weight(type.getMaxWeight())
                .price(type.getPrice())
                .status(BaggageStatus.RESERVED)
                .passengerId(requestDto.getPassengerId())
                .build();

        Baggage saved = baggageRepository.save(baggage);
        System.out.println("************** "+saved.toString());
        return baggageMapper.toDto(saved);
    }

    public List<BaggageResponseDto> getBaggagesByBookingId(UUID bookingId) {
        return baggageRepository.findAllByBookingId(bookingId).stream()
                .map(baggageMapper::toDto)
                .collect(Collectors.toList());
    }

    public BaggageResponseDto checkInBaggage( BaggageCheckinRequestDto baggageCheckinRequestDto) {
        Baggage baggage = baggageRepository.findById(baggageCheckinRequestDto.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Baggage not found"));
        baggage.setStatus(BaggageStatus.CHECKED_IN);
        Baggage updated = baggageRepository.save(baggage);
        return baggageMapper.toDto(updated);
    }
}
