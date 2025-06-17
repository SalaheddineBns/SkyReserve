package com.salah.checkinservice.service;


import com.salah.checkinservice.client.*;
import com.salah.checkinservice.dto.*;
import com.salah.checkinservice.entity.Checkin;
import com.salah.checkinservice.repository.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckinServiceImpl implements CheckinService {

    private final CheckinRepository checkinRepository;
    private final BookingClient bookingClient;
    private final InventoryClient inventoryClient;
    private final BaggageClient baggageClient;
    private final FlightClient flightClient;

    private static final Duration CHECKIN_OPEN_BEFORE_FLIGHT = Duration.ofHours(48);
    private static final Duration CHECKIN_CLOSE_BEFORE_FLIGHT = Duration.ofMinutes(30);
    private static final Duration BAGGAGE_OPEN_BEFORE_FLIGHT = Duration.ofHours(2);

    @Override
    public List<String> getAvailableSeats(Long flightId) {
        return inventoryClient.getAvailableSeats(flightId);
    }


    @Override
    public BookingDto getBookingDetails(UUID bookingId) {
        return bookingClient.getBooking(bookingId);
    }


    @Override
    public CheckinResponseDto performRandomCheckin(UUID bookingId) {
        return performCheckin(bookingId, null, "", "");
    }

    @Override
    public CheckinResponseDto performCheckin(UUID bookingId, String preferredSeat, String firstName, String lastName) {
        BookingDto booking = bookingClient.getBooking(bookingId);
        FlightDto flight = flightClient.getFlight(booking.getFlightId());
        LocalDateTime flightTime = LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime()); // ✨ 这里定义了 flightTime
        LocalDateTime now = LocalDateTime.now();

        boolean passengerExists = booking.getPassengers().stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName));


        if (!"CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Check-in not allowed: booking not confirmed (unpaid).");
        }

        if (!passengerExists) {
            throw new IllegalArgumentException("Passenger not found in booking.");
        }

        // Check-in for seats must be between 48h and 30min before flight
        if (now.isBefore(flightTime.minus(CHECKIN_OPEN_BEFORE_FLIGHT)) ||
                now.isAfter(flightTime.minus(CHECKIN_CLOSE_BEFORE_FLIGHT))) {
            throw new IllegalStateException("Seat check-in is only allowed between 48h and 30min before departure.");
        }

        // If passenger has SOUTE baggage, validate time range and call baggage service
        boolean hasSoute = booking.getPassengers().stream()
                .flatMap(p -> p.getBaggageOptions().stream())
                .anyMatch(opt -> "SOUTE".equalsIgnoreCase(opt.getType()) && opt.getQuantity() > 0);
        if (hasSoute) {
            if (now.isBefore(flightTime.minus(BAGGAGE_OPEN_BEFORE_FLIGHT)) ||
                    now.isAfter(flightTime.minus(CHECKIN_CLOSE_BEFORE_FLIGHT))) {
                throw new IllegalStateException("Baggage check-in is only allowed between 2h and 30min before departure.");
            }

            // ✅ 调用远程行李登记服务
            baggageClient.checkinBaggage(bookingId,
                    new BaggageCheckinRequestDto(firstName, lastName));


            if (!baggageClient.validateBaggage(bookingId)) {
                throw new IllegalStateException("Baggage validation failed.");
            }
        }

        List<String> availableSeats = inventoryClient.getAvailableSeats(booking.getFlightId());

        if (availableSeats.isEmpty()) {
            throw new IllegalStateException("No available seats.");
        }

        String assignedSeat = (preferredSeat != null && availableSeats.contains(preferredSeat))
                ? preferredSeat : availableSeats.get(0);

        inventoryClient.assignSeat(booking.getFlightId(), assignedSeat);

        Checkin checkin = new Checkin();
        checkin.setBookingId(bookingId);
        checkin.setSeatNumber(assignedSeat);
        checkin.setBoardingPassNumber(UUID.randomUUID());
        checkin.setCheckinTime(now);
        checkin.setFirstName(firstName);
        checkin.setLastName(lastName);

        checkinRepository.save(checkin);

        return new CheckinResponseDto(checkin);



    }

    @Override
    public List<CheckinResponseDto> getCheckinsByBookingId(UUID bookingId) {
        return checkinRepository.findByBookingId(bookingId)
                .stream().map(CheckinResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public BoardingPassDto generateBoardingPass(UUID boardingPassNumber) {
        Checkin checkin = checkinRepository.findByBoardingPassNumber(boardingPassNumber)
                .orElseThrow(() -> new IllegalArgumentException("No check-in record found for this boarding pass number."));

        BookingDto booking = bookingClient.getBooking(checkin.getBookingId());

        FlightDto flight = flightClient.getFlight(booking.getFlightId());

        LocalDateTime flightTime = LocalDateTime.of(
                flight.getDepartureDate(),
                flight.getDepartureTime()
        );

        return BoardingPassDto.builder()
                .passengerName(checkin.getFirstName() + " " + checkin.getLastName())
                .flightNumber(booking.getFlightNumber())
                .seatNumber(checkin.getSeatNumber())
                .flightTime(flightTime)
                .gate("B12")
                .boardingPassNumber(checkin.getBoardingPassNumber())
                .build();
    }


}
