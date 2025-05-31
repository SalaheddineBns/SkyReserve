package com.salah.paymentservice.mapper;

import com.salah.paymentservice.dto.PaymentRequestDto;
import com.salah.paymentservice.dto.PaymentResponseDto;
import com.salah.paymentservice.model.Payment;
import com.salah.paymentservice.model.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    // DTO → Entity
    public Payment toEntity(PaymentRequestDto request) {
        return Payment.builder()
                .bookingId(request.bookingId())
                .amount(request.amount())
                .status(PaymentStatus.COMPLETED)
                .build();
    }

    // Entity → DTO
    public PaymentResponseDto toResponse(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }
}
