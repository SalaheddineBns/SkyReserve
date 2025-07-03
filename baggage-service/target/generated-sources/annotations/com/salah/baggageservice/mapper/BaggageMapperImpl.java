package com.salah.baggageservice.mapper;

import com.salah.baggageservice.dto.BaggageRequestDto;
import com.salah.baggageservice.dto.BaggageResponseDto;
import com.salah.baggageservice.model.Baggage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-03T14:17:25+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class BaggageMapperImpl implements BaggageMapper {

    @Override
    public BaggageResponseDto toDto(Baggage baggage) {
        if ( baggage == null ) {
            return null;
        }

        BaggageResponseDto baggageResponseDto = new BaggageResponseDto();

        baggageResponseDto.setBookingId( baggage.getBookingId() );
        baggageResponseDto.setPassengerId( baggage.getPassengerId() );
        baggageResponseDto.setType( baggage.getType() );
        baggageResponseDto.setWeight( baggage.getWeight() );
        baggageResponseDto.setPrice( baggage.getPrice() );
        baggageResponseDto.setStatus( baggage.getStatus() );
        baggageResponseDto.setId( baggage.getId() );

        return baggageResponseDto;
    }

    @Override
    public Baggage toEntity(BaggageRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Baggage.BaggageBuilder baggage = Baggage.builder();

        baggage.bookingId( dto.getBookingId() );
        baggage.passengerId( dto.getPassengerId() );
        baggage.type( dto.getType() );
        baggage.weight( dto.getWeight() );
        baggage.price( dto.getPrice() );

        return baggage.build();
    }
}
