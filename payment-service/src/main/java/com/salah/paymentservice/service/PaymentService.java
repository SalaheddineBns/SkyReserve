package com.salah.paymentservice.service;

import com.salah.paymentservice.dto.PaymentRequestDto;
import com.salah.paymentservice.dto.PaymentResponseDto;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto request);

    PaymentResponseDto getPaymentById(UUID id);

    List<PaymentResponseDto> getAllPayments();
}
