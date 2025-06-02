package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.dto.PassengerDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // 🎯 Nouvelle méthode pour enrichir BookingResponseDto avec passagers
    @Mapping(target = "passengers", source = "passengers")
    BookingResponseDto toDto(Booking booking, List<PassengerDto> passengers);
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

    @AfterMapping
    default void enrichBookingResponseWithPassengers(Booking booking, @MappingTarget BookingResponseDto response) {
        // Passengers ne sont pas en BDD, donc on ne peut pas les récupérer.
        // On met une liste vide pour éviter null
        response = new BookingResponseDto(
                response.bookingId(),
                response.flightId(),
                response.seats(),
                response.totalPrice(),
                response.status(),
                response.bookingDate(),
                response.expirationDate(),
                response.firstName(),
                response.lastName(),
                response.email(),
                response.phone(),
                response.civility(),
                List.of() // 🎯 Passagers vide par défaut
        );
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
