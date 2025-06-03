package com.salah.paymentservice.mapper;

import com.salah.paymentservice.dto.PaymentResponseDto;
import com.salah.paymentservice.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

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
