package com.salah.bookingservice.util;

import com.salah.bookingservice.client.InventoryClient;
import com.salah.bookingservice.dto.SeatReleaseRequestDto;
import com.salah.bookingservice.model.Booking;
import com.salah.bookingservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingExpirationScheduler {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Scheduled(fixedRate = 60000) // Toutes les 60 secondes
    public void cancelExpiredBookings() {
        List<Booking> expiredBookings = bookingRepository.findByStatusAndExpirationDateBefore("PENDING", LocalDateTime.now());
        for (Booking booking : expiredBookings) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);


            // Libération des sièges
            SeatReleaseRequestDto seatRequest = new SeatReleaseRequestDto(booking.getFlightId(), booking.getSeats());
            inventoryClient.releaseSeats(seatRequest);


            System.out.println("Booking " + booking.getBookingId() + " cancelled (expired).");
        }
    }
}

