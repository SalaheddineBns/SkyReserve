package com.salah.paymentservice.dto;

import com.salah.paymentservice.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDto(UUID id, UUID bookingId, double amount, PaymentStatus status, LocalDateTime createdAt) { }