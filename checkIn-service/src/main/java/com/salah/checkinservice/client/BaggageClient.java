package com.salah.checkinservice.client;

import com.salah.checkinservice.dto.BaggageCheckinRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "baggage-service")
public interface BaggageClient {
    @GetMapping("/api/baggages/validate/{bookingId}")
    boolean validateBaggage(@PathVariable UUID bookingId);

    @PostMapping("/api/baggages/checkin/{bookingId}")
    void checkinBaggage(@PathVariable UUID bookingId,
                        @RequestBody BaggageCheckinRequestDto request);

}