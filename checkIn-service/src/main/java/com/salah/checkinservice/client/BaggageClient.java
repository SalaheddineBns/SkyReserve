package com.salah.checkinservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@FeignClient(name = "baggage-service")
public interface BaggageClient {
    @GetMapping("/api/baggages/validate/{bookingId}")
    boolean validateBaggage(@PathVariable UUID bookingId);
}