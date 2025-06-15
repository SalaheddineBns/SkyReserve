package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.dto.PassengerDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking toEntity(BookingRequestDto requestDto);

    // on n’utilise plus cette méthode car elle ne marche pas avec les records et deux sources :
    // BookingResponseDto toDto(Booking booking, List<PassengerDto> passengers);

    default BookingResponseDto toDto(Booking booking, List<PassengerDto> passengers) {
        return new BookingResponseDto(
                booking.getBookingId(),
                booking.getFlightId(),
                booking.getSeats(),
                booking.getTotalPrice(),
                booking.getStatus(),
                booking.getBookingDate(),
                booking.getExpirationDate(),
                booking.getFirstName(),
                booking.getLastName(),
                booking.getEmail(),
                booking.getPhone(),
                booking.getCivility(),
                passengers
        );
    }

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
}
