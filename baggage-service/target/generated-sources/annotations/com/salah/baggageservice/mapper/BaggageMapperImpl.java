package com.salah.baggageservice.mapper;

import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.model.Baggage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-29T17:17:55+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class BaggageMapperImpl implements BaggageMapper {

    @Override
    public Baggage toEntity(BaggageRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Baggage.BaggageBuilder baggage = Baggage.builder();

        baggage.bookingId( requestDto.getBookingId() );
        baggage.type( requestDto.getType() );
        baggage.weight( requestDto.getWeight() );
        baggage.price( requestDto.getPrice() );

        return baggage.build();
    }

    @Override
    public BaggageResponseDto toDto(Baggage baggage) {
        if ( baggage == null ) {
            return null;
        }

        BaggageResponseDto baggageResponseDto = new BaggageResponseDto();

        baggageResponseDto.setId( baggage.getId() );
        baggageResponseDto.setBookingId( baggage.getBookingId() );
        baggageResponseDto.setType( baggage.getType() );
        baggageResponseDto.setWeight( baggage.getWeight() );
        baggageResponseDto.setPrice( baggage.getPrice() );
        baggageResponseDto.setStatus( baggage.getStatus() );

        return baggageResponseDto;
    }
}
