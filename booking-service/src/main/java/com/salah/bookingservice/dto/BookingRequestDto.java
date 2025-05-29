package com.salah.bookingservice.dto;


import java.util.List;

public record BookingRequestDto(
        Long flightId,
       // Long userId,
        int seats,
        List<BaggageOption> baggageOptions,
        UserInfoDto userInfos
) {}


