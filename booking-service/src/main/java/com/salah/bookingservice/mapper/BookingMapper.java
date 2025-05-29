package com.salah.bookingservice.mapper;

import com.salah.bookingservice.dto.BookingResponseDto;
import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingResponseDto toDto(Booking booking);
    //Booking toEntity(BookingDto bookingDto);
    @Mapping(target = "firstName", source = "userInfos.firstName")
    @Mapping(target = "lastName", source = "userInfos.lastName")
    @Mapping(target = "email", source = "userInfos.email")
    @Mapping(target = "phone", source = "userInfos.phone")
    @Mapping(target = "civility", source = "userInfos.civility")
    Booking toEntity(BookingRequestDto requestDto);
}
