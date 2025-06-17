package com.salah.bookingservice.service;

import com.salah.bookingservice.client.BaggageClient;
import com.salah.bookingservice.client.FlightClient;
import com.salah.bookingservice.client.InventoryClient;
import com.salah.bookingservice.dto.*;
import com.salah.bookingservice.mapper.BookingMapper;
import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.model.Passenger;
import com.salah.bookingservice.repository.BookingRepository;
import com.salah.bookingservice.repository.PassengerRepository;
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
    private PassengerRepository passengerRepository;

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
        // 1. Vérifier le vol
        FlightDto flight = flightClient.getFlightById(request.flightId());
        if (flight == null) throw new IllegalArgumentException("Flight not found");

        // 2. Vérifier disponibilité des sièges
        boolean seatsAvailable = inventoryClient.checkAvailability(request.flightId(), request.seats());
        if (!seatsAvailable) throw new IllegalArgumentException("Not enough seats available");

        // 3. Créer l'entité booking
        Booking booking = bookingMapper.toEntity(request);

        // 4. Calculer le prix
        List<BaggageOption> allBaggageOptions = request.passengers().stream()
                .filter(p -> p.baggageOptions() != null)
                .flatMap(p -> p.baggageOptions().stream())
                .collect(Collectors.toList());

        booking.setTotalPrice(priceCalculator.calculateTotalPrice(flight, request.seats(), allBaggageOptions));
        booking.setBookingDate(LocalDateTime.now());
        booking.setExpirationDate(LocalDateTime.now().plusMinutes(10));
        booking.setStatus("PENDING");
        booking.setFlightId(request.flightId());

        // ✅ Ajouter les infos du passager principal
        if (request.passengers() != null && !request.passengers().isEmpty()) {
            PassengerDto mainPassenger = request.passengers().get(0);
            booking.setFirstName(mainPassenger.firstName());
            booking.setLastName(mainPassenger.lastName());
            booking.setEmail(mainPassenger.email());
            booking.setPhone(mainPassenger.phone());
            booking.setCivility(mainPassenger.civilite());
        }

        Booking savedBooking = bookingRepository.save(booking);

        // 5. Sauvegarde des passagers
        if (request.passengers() != null) {
            List<Passenger> passengers = request.passengers().stream().map(dto -> {
                Passenger p = new Passenger();
                p.setFirstName(dto.firstName());
                p.setLastName(dto.lastName());
                p.setEmail(dto.email());
                p.setPhone(dto.phone());
                p.setCivilite(dto.civilite());
                p.setBooking(savedBooking);
                return p;
            }).toList();

            passengerRepository.saveAll(passengers);
            savedBooking.setPassengers(passengers);
        }

        // 6. Réserver les bagages
        if (request.passengers() != null) {
            for (PassengerDto passenger : request.passengers()) {
                if (passenger.baggageOptions() != null) {
                    for (BaggageOption option : passenger.baggageOptions()) {
                        for (int i = 0; i < option.quantity(); i++) {
                            BaggageRequestDto baggageRequest = new BaggageRequestDto(
                                    savedBooking.getBookingId(),
                                    option.type(),
                                    null,
                                    null
                            );
                            baggageClient.reserveBaggage(baggageRequest);
                        }
                    }
                }
            }
        }

        // 7. Réserver les sièges
        inventoryClient.reserveSeats(new SeatReservationRequestDto(request.flightId(), request.seats()));

        return bookingMapper.toDto(savedBooking, request.passengers());
    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> {
                    List<PassengerDto> passengerDtos = booking.getPassengers().stream().map(p ->
                            new PassengerDto(
                                    p.getFirstName(),
                                    p.getLastName(),
                                    p.getEmail(),
                                    p.getPhone(),
                                    p.getCivilite(),
                                    List.of() // Ajoute les bagages plus tard si besoin
                            )
                    ).toList();
                    return bookingMapper.toDto(booking, passengerDtos);
                })
                .collect(Collectors.toList());
    }

    public BookingResponseDto getBookingById(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        List<Passenger> passengers = passengerRepository.findByBooking(booking);
        List<PassengerDto> passengerDtos = passengers.stream().map(p ->
                new PassengerDto(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getEmail(),
                        p.getPhone(),
                        p.getCivilite(),
                        List.of()
                )
        ).toList();

        return bookingMapper.toDto(booking, passengerDtos);
    }

    @Transactional
    public void updateBookingStatus(UUID bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }
}
