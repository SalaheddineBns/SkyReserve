package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingDto;
import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto toDto(Booking booking);
    Booking toEntity(BookingDto bookingDto);

    Booking toEntity(BookingRequestDto requestDto);
}
