package com.salah.baggageservice.mapper;

import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.model.Baggage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-15T18:30:37+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class BaggageMapperImpl implements BaggageMapper {

    @Override
    public Baggage toEntity(BaggageRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Baggage baggage = new Baggage();

        return baggage;
    }

    @Override
    public BaggageResponseDto toDto(Baggage baggage) {
        if ( baggage == null ) {
            return null;
        }

        BaggageResponseDto baggageResponseDto = new BaggageResponseDto();

        return baggageResponseDto;
    }
}
