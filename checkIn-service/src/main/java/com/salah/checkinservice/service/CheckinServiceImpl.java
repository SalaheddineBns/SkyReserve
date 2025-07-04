package com.salah.checkinservice.service;


import com.salah.checkinservice.client.*;
import com.salah.checkinservice.dto.*;
import com.salah.checkinservice.entity.Checkin;
import com.salah.checkinservice.repository.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private  CheckinRepository checkinRepository;
    @Autowired
    private  BookingClient bookingClient;
    @Autowired
    private  InventoryClient inventoryClient;
    @Autowired
    private  BaggageClient baggageClient;
    @Autowired
    private  FlightClient flightClient;

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


//    @Override
//    public CheckinResponseDto performRandomCheckin(UUID bookingId) {
//        return performCheckin(bookingId, null, "", "");
//    }

    @Override
    public CheckinResponseDto performCheckin(UUID bookingId, String preferredSeat, Long passengerId) {
        BookingDto booking = bookingClient.getBooking(bookingId);
        FlightDto flight = flightClient.getFlight(booking.getFlightId());
        LocalDateTime flightTime = flight.getDepartureTime();
        LocalDateTime now = LocalDateTime.now();

        // 1. Vérification existence passager
        Optional<PassengerDto> passengerOpt = booking.getPassengers().stream()
                .filter(p -> Objects.equals(p.getPassengerId(), passengerId))
                .findFirst();

        if (passengerOpt.isEmpty()) {
            throw new IllegalArgumentException("Passenger not found in booking.");
        }

        PassengerDto passenger = passengerOpt.get();

        // 2. Vérification statut booking
        if (!"CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
            throw new IllegalStateException("Check-in not allowed: booking not confirmed (unpaid).");
        }

        // 3. Vérification plage horaire check-in
        if (now.isBefore(flightTime.minus(CHECKIN_OPEN_BEFORE_FLIGHT)) ||
                now.isAfter(flightTime.minus(CHECKIN_CLOSE_BEFORE_FLIGHT))) {
            throw new IllegalStateException("Seat check-in is only allowed between 48h and 30min before departure.");
        }

        // 4. Check-in bagage SOUTE si présent
        boolean hasSoute = passenger.getBaggageOptions().stream()
                .anyMatch(opt -> "SOUTE".equalsIgnoreCase(opt.getType()) && opt.getQuantity() > 0);

        if (hasSoute) {
            if (now.isBefore(flightTime.minus(BAGGAGE_OPEN_BEFORE_FLIGHT)) ||
                    now.isAfter(flightTime.minus(CHECKIN_CLOSE_BEFORE_FLIGHT))) {
                throw new IllegalStateException("Baggage check-in is only allowed between 2h and 30min before departure.");
            }

            baggageClient.checkinBaggage(new BaggageCheckinRequestDto(passengerId, bookingId));

            if (!baggageClient.validateBaggage(bookingId)) {
                throw new IllegalStateException("Baggage validation failed.");
            }
        }

        // 5. Gestion du siège
        List<String> availableSeats = inventoryClient.getAvailableSeats(booking.getFlightId());
        if (availableSeats.isEmpty()) {
            throw new IllegalStateException("Aucun siège disponible.");
        }

        String assignedSeat;

        if (preferredSeat != null && !preferredSeat.isBlank()) {
            if (!availableSeats.contains(preferredSeat)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le siège \"" + preferredSeat + "\" n’est pas disponible. Veuillez en choisir un autre.");
            }

            assignedSeat = preferredSeat;
        } else {
            assignedSeat = availableSeats.get(0); // ou utiliser Random si tu veux plus d’aléatoire
        }

        inventoryClient.assignSeatToPassenger(
                new SeatAssignmentRequestDto(booking.getFlightId(), assignedSeat, passengerId)
        );

        // 6. Enregistrement du check-in
        Checkin checkin = new Checkin();
        checkin.setBookingId(bookingId);
        checkin.setSeatNumber(assignedSeat);
        checkin.setBoardingPassNumber(UUID.randomUUID());
        checkin.setCheckinTime(now);
        checkin.setPassengerId(passengerId);
        checkin.setFirstName(passenger.getFirstName());
        checkin.setLastName(passenger.getLastName());
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

        // ✅ Plus besoin de construire LocalDateTime à partir de deux champs
        LocalDateTime flightTime = flight.getDepartureTime();

        return BoardingPassDto.builder()
                .passengerName(checkin.getFirstName() + " " + checkin.getLastName())
                .flightNumber(booking.getFlightNumber())
                .seatNumber(checkin.getSeatNumber())
                .flightTime(flightTime)
                .gate("B12")
                .boardingPassNumber(checkin.getBoardingPassNumber())
                .build();
    }


    public List<CheckinResponseDto> getAllCheckins() {
        return checkinRepository.findAll()
                .stream()
                .map(CheckinResponseDto::new)
                .collect(Collectors.toList());
    }


}
