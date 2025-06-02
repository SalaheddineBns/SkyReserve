package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.dto.PassengerDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface BookingMapper {
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
}
