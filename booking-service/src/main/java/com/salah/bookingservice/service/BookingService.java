package com.salah.bookingservice.service;

import com.salah.bookingservice.client.BaggageClient;
import com.salah.bookingservice.client.FlightClient;
import com.salah.bookingservice.client.InventoryClient;
import com.salah.bookingservice.dto.*;
import com.salah.bookingservice.mapper.BookingMapper;
import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.repository.BookingRepository;
import com.salah.bookingservice.util.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightClient flightClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private BaggageClient baggageClient;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private PriceCalculator priceCalculator;

    public BookingResponseDto createBooking(BookingRequestDto request) {
        // 1️⃣ Vérifier le vol (FlightService)
        FlightDto flight = flightClient.getFlightById(request.flightId());
        if (flight == null) {
            throw new IllegalArgumentException("Flight not found");
        }

        // 2️⃣ Vérifier la disponibilité des sièges (InventoryService)
        boolean seatsAvailable = inventoryClient.checkAvailability(request.flightId(), request.seats());
        if (!seatsAvailable) {
            throw new IllegalArgumentException("Not enough seats available");
        }

        // 3️⃣ Créer le booking en base (status = PENDING)
        Booking booking = bookingMapper.toEntity(request);
        booking.setTotalPrice(
                priceCalculator.calculateTotalPrice(flight, request.seats(), request.baggageOptions())
        );
        booking.setBookingDate(LocalDateTime.now());
        booking.setExpirationDate(LocalDateTime.now().plusMinutes(10)); // ⏳ Expire dans 10 min
        booking.setStatus("PENDING");

        UserInfoDto userInfo = request.userInfos();
        booking.setFirstName(userInfo.firstName());
        booking.setLastName(userInfo.lastName());
        booking.setEmail(userInfo.email());
        booking.setPhone(userInfo.phone());
        booking.setCivility(userInfo.civility());

        Booking savedBooking = bookingRepository.save(booking);

        // 4️⃣ Gérer les bagages (BaggageService)
        if (request.baggageOptions() != null && !request.baggageOptions().isEmpty()) {
            for (BaggageOption option : request.baggageOptions()) {
                for (int i = 0; i < option.quantity(); i++) {
                    BaggageRequestDto baggageRequest = new BaggageRequestDto();
                    baggageRequest.setBookingId(savedBooking.getBookingId());
                    baggageRequest.setType(option.type()); // car BaggageRequestDto utilise String pour le type
                    // Pas de poids ni de prix ici car pas fourni dans BookingRequestDto, à calculer côté BaggageService si besoin

                    baggageClient.reserveBaggage(baggageRequest);
                }
            }
        }



        // 5️⃣ Réserver les sièges après succès
        SeatReservationRequestDto seatRequest = new SeatReservationRequestDto(request.flightId(), request.seats());
        inventoryClient.reserveSeats(seatRequest);

        // 6️⃣ Retourner le résultat
        return bookingMapper.toDto(savedBooking);
    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
