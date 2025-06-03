package com.salah.paymentservice.client;

import com.salah.paymentservice.dto.BookingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "booking-service")   // ⚠️ 这里的名字要和 Eureka 注册名一致
public interface BookingClient {

    @GetMapping("/api/bookings/{bookingId}")
    BookingResponseDto getBookingById(@PathVariable UUID bookingId);

    @PutMapping("/api/bookings/{bookingId}/status")
    void updateBookingStatus(@PathVariable UUID bookingId, @RequestParam String status);

}
