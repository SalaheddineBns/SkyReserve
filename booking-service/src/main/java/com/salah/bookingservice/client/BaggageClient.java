package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.BaggageRequestDto;
import com.salah.bookingservice.dto.BaggageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "baggage-service")
public interface BaggageClient {

    @PostMapping("/api/baggages/reserve")
    BaggageResponseDto reserveBaggage(@RequestBody BaggageRequestDto requestDto);

    @GetMapping("/api/baggages/booking/{bookingId}")
    List<BaggageResponseDto> getBaggageByBooking(@PathVariable("bookingId") Long bookingId);

    @PostMapping("/api/baggages/{baggageId}/checkin")
    BaggageResponseDto checkInBaggage(@PathVariable("baggageId") Long baggageId);
}
