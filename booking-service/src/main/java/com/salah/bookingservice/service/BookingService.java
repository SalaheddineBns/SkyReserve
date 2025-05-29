package com.salah.bookingservice.service;

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
    private InventoryClient inventoryClient; // Appelle le service Inventory

//    @Autowired
//    private BaggageClient baggageClient; // Appelle le service Baggage

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private PriceCalculator priceCalculator; // Ton calculateur de prix

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

        // 3️⃣ Gérer les bagages (BaggageService)
//        if (request.baggageOptions() != null && !request.baggageOptions().isEmpty()) {
//            baggageClient.reserveBaggage(request.flightId(), request.userId(), request.baggageOptions());
//        }

        // 4️⃣ Calculer le prix total (siège + bagages)
        double totalPrice = priceCalculator.calculateTotalPrice(flight, request.seats(), request.baggageOptions());

        // 5️⃣ Créer le booking en base (status = PENDING)
        Booking booking = bookingMapper.toEntity(request);
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDateTime.now());
        booking.setExpirationDate(LocalDateTime.now().plusMinutes(10)); // ⏳ Expire dans 10 min
        booking.setStatus("PENDING");

        UserInfoDto userInfo = request.userInfos();
        booking.setFirstName(userInfo.firstName());
        booking.setLastName(userInfo.lastName());
        booking.setEmail(userInfo.email());
        booking.setPhone(userInfo.phone());
        booking.setCivility(userInfo.civility());

        Booking saved = bookingRepository.save(booking);

        // 6️⃣ (Optionnel) Réserver les sièges après succès
        SeatReservationRequestDto seatRequest = new SeatReservationRequestDto(request.flightId(), request.seats());
        inventoryClient.reserveSeats(seatRequest);

        // 7️⃣ Retourner le résultat
        return bookingMapper.toDto(saved);
    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
