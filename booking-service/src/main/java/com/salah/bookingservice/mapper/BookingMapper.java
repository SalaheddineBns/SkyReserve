package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingResponseDto toDto(Booking booking);

    Booking toEntity(BookingRequestDto requestDto);

    @AfterMapping
    default void enrichBookingWithPassengerInfo(BookingRequestDto requestDto, @MappingTarget Booking booking) {
        if (requestDto.passengers() != null && !requestDto.passengers().isEmpty()) {
            var passenger = requestDto.passengers().get(0);
            booking.setFirstName(passenger.firstName());
            booking.setLastName(passenger.lastName());
            booking.setEmail(passenger.email());
            booking.setPhone(passenger.phone());
            booking.setCivility(passenger.civilite());
        }
    }

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
