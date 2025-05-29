package com.salah.baggageservice.mapper;

import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.model.Baggage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BaggageMapper {
    Baggage toEntity(BaggageRequestDto requestDto);
    BaggageResponseDto toDto(Baggage baggage);
}
