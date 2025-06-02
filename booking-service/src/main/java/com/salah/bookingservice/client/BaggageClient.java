package com.salah.bookingservice.client;

import com.salah.bookingservice.dto.BaggageRequestDto;
import com.salah.bookingservice.dto.BaggageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "baggage-service", url = "http://localhost:8086/api/baggages")
public interface BaggageClient {

    @PostMapping("/reserve")
    BaggageResponseDto reserveBaggage(@RequestBody BaggageRequestDto requestDto);

    @GetMapping("/booking/{bookingId}")
    List<BaggageResponseDto> getBaggageByBooking(@PathVariable("bookingId") UUID bookingId);

    @PostMapping("/{baggageId}/checkin")
    BaggageResponseDto checkInBaggage(@PathVariable("baggageId") Long baggageId);
}
