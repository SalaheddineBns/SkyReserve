package com.salah.checkinservice.client;

import com.salah.checkinservice.dto.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "booking-service")
public interface BookingClient {
    @GetMapping("/api/bookings/{bookingId}")
    BookingDto getBooking(@PathVariable UUID bookingId);
}