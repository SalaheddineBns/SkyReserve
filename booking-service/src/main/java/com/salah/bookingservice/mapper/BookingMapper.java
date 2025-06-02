package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "bookingId", source = "bookingId")
    @Mapping(target = "flightId", source = "flightId")
    @Mapping(target = "seats", source = "seats")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "bookingDate", source = "bookingDate")
    @Mapping(target = "expirationDate", source = "expirationDate")
    @Mapping(target = "baggagesFees", source = "baggagesFees")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "civility", source = "civility")
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
