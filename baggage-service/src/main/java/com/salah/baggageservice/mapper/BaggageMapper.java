package com.salah.baggageservice.mapper;

import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.model.Baggage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BaggageMapper {

    @Mapping(source = "bookingId", target = "bookingId")
    @Mapping(source = "passengerId", target = "passengerId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "status", target = "status")
    BaggageResponseDto toDto(Baggage baggage);

    @Mapping(source = "bookingId", target = "bookingId")
    @Mapping(source = "passengerId", target = "passengerId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "price", target = "price")
    Baggage toEntity(BaggageRequestDto dto);
}

