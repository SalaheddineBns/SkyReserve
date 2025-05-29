package com.salah.bookingservice.dto;

import com.salah.bookingservice.model.enums.BaggageType;

public record BaggageOption(
        BaggageType type,  // Par exemple : CABIN, SOUTE, SPECIAL
        int quantity       // Nombre d'unités de ce type
) {}

