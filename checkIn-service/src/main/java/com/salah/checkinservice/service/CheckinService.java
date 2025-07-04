package com.salah.checkinservice.service;

import com.salah.checkinservice.dto.BoardingPassDto;
import com.salah.checkinservice.dto.BookingDto;
import com.salah.checkinservice.dto.CheckinResponseDto;

import java.util.List;
import java.util.UUID;

public interface CheckinService {

    List<String> getAvailableSeats(Long flightId);

    //CheckinResponseDto performRandomCheckin(UUID bookingId);


    CheckinResponseDto performCheckin(UUID bookingId, String seatNumber, Long passengerId);

    List<CheckinResponseDto> getCheckinsByBookingId(UUID bookingId);

    BookingDto getBookingDetails(UUID bookingId);

    BoardingPassDto generateBoardingPass(UUID boardingPassNumber);
    List<CheckinResponseDto> getAllCheckins(); // ✅ Signature ajoutée

}