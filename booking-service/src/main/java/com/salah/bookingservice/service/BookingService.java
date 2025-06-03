package com.salah.bookingservice.service;

import com.salah.bookingservice.client.BaggageClient;
import com.salah.bookingservice.client.FlightClient;
import com.salah.bookingservice.client.InventoryClient;
import com.salah.bookingservice.dto.*;
import com.salah.bookingservice.mapper.BookingMapper;
import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.repository.BookingRepository;
import com.salah.bookingservice.util.PriceCalculator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
        // 1️⃣ Récupérer tous les bagages de tous les passagers en une seule liste
        List<BaggageOption> allBaggageOptions = request.passengers().stream()
                .filter(passenger -> passenger.baggageOptions() != null)
                .flatMap(passenger -> passenger.baggageOptions().stream())
                .collect(Collectors.toList());

// 2️⃣ Appeler le calcul du prix avec ces bagages
        booking.setTotalPrice(
                priceCalculator.calculateTotalPrice(flight, request.seats(), allBaggageOptions)
        );

        booking.setBookingDate(LocalDateTime.now());
        booking.setExpirationDate(LocalDateTime.now().plusMinutes(10)); // ⏳ Expire dans 10 min
        booking.setStatus("PENDING");

        // 🧑‍✈️ Ajouter le premier passager comme info principale (optionnel)
        if (request.passengers() != null && !request.passengers().isEmpty()) {
            PassengerDto mainPassenger = request.passengers().get(0);
            booking.setFirstName(mainPassenger.firstName());
            booking.setLastName(mainPassenger.lastName());
            booking.setEmail(mainPassenger.email());
            booking.setPhone(mainPassenger.phone());
            booking.setCivility(mainPassenger.civilite());
        }

        Booking savedBooking = bookingRepository.save(booking);

        // 4️⃣ Gérer les bagages de chaque passager
        if (request.passengers() != null && !request.passengers().isEmpty()) {
            for (PassengerDto passenger : request.passengers()) {
                if (passenger.baggageOptions() != null) {
                    for (BaggageOption option : passenger.baggageOptions()) {
                        for (int i = 0; i < option.quantity(); i++) {
                            BaggageRequestDto baggageRequest = new BaggageRequestDto(
                                    savedBooking.getBookingId(),
                                    option.type(),
                                    null, // poids optionnel
                                    null  // prix optionnel
                            );
                            baggageClient.reserveBaggage(baggageRequest);
                        }
                    }
                }
            }
        }

        // 5️⃣ Réserver les sièges après succès
        SeatReservationRequestDto seatRequest = new SeatReservationRequestDto(request.flightId(), request.seats());
        inventoryClient.reserveSeats(seatRequest);

        // 6️⃣ Retourner le résultat
        BookingResponseDto response = bookingMapper.toDto(savedBooking, request.passengers());
        return response;


    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> bookingMapper.toDto(booking, List.of())) // Passengers = vide
                .collect(Collectors.toList());
    }

    //  Il faut ajouter ceci !!!
    public BookingResponseDto getBookingById(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Vous pouvez également appeler BaggageClient pour enregistrer les passagers.
        // Ici, nous passons d'abord List.of(), lorsque nous aurons le temps d'améliorer la logique de chargement des passagers.
        return bookingMapper.toDto(booking, List.of());
    }

    @Transactional
    public void updateBookingStatus(UUID bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }


}
