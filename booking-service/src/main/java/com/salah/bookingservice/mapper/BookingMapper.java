package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingResponseDto toDto(Booking booking);

    @Mapping(target = "firstName", expression = "java(getFirstPassengerFirstName(requestDto))")
    @Mapping(target = "lastName", expression = "java(getFirstPassengerLastName(requestDto))")
    @Mapping(target = "email", expression = "java(getFirstPassengerEmail(requestDto))")
    @Mapping(target = "phone", expression = "java(getFirstPassengerPhone(requestDto))")
    @Mapping(target = "civility", expression = "java(getFirstPassengerCivility(requestDto))")
    Booking toEntity(BookingRequestDto requestDto);

    // Méthodes helpers
    default String getFirstPassengerFirstName(BookingRequestDto requestDto) {
        return (requestDto.passengers() != null && !requestDto.passengers().isEmpty())
                ? requestDto.passengers().get(0).firstName()
                : null;
    }

    default String getFirstPassengerLastName(BookingRequestDto requestDto) {
        return (requestDto.passengers() != null && !requestDto.passengers().isEmpty())
                ? requestDto.passengers().get(0).lastName()
                : null;
    }

    default String getFirstPassengerEmail(BookingRequestDto requestDto) {
        return (requestDto.passengers() != null && !requestDto.passengers().isEmpty())
                ? requestDto.passengers().get(0).email()
                : null;
    }

    default String getFirstPassengerPhone(BookingRequestDto requestDto) {
        return (requestDto.passengers() != null && !requestDto.passengers().isEmpty())
                ? requestDto.passengers().get(0).phone()
                : null;
    }

    default String getFirstPassengerCivility(BookingRequestDto requestDto) {
        return (requestDto.passengers() != null && !requestDto.passengers().isEmpty())
                ? requestDto.passengers().get(0).civilite()
                : null;
    }
}
