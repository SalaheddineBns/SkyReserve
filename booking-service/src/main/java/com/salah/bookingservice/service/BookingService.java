package com.salah.bookingservice.service;

import com.salah.bookingservice.client.FlightClient;
import com.salah.bookingservice.dto.BookingDto;
import com.salah.bookingservice.dto.FlightDto;
import com.salah.bookingservice.dto.BookingRequestDto;
import com.salah.bookingservice.mapper.BookingMapper;
import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.repository.BookingRepository;
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
    private FlightClient flightClient; // Feign client qui appelle le service Flight

    @Autowired
    private BookingMapper bookingMapper;

    public BookingDto createBooking(BookingRequestDto request) {
        // 1️⃣ Vérifier le vol et son prix
        FlightDto flight = flightClient.getFlightById(request.flightId());
        if (flight == null) {
            throw new IllegalArgumentException("Flight not found");
        }

        double seatPrice = flight.price();
        double totalPrice = seatPrice * request.seats();

        // 2️⃣ Mapper le DTO vers l'entité Booking
        Booking booking = bookingMapper.toEntity(request);
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");

        Booking saved = bookingRepository.save(booking);

        // 3️⃣ Mapper l'entité sauvegardée vers le DTO
        return bookingMapper.toDto(saved);
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
