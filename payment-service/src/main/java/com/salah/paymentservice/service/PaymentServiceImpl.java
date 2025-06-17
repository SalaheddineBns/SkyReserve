package com.salah.paymentservice.service;

import com.salah.paymentservice.client.BookingClient;
import com.salah.paymentservice.client.NotificationClient;
import com.salah.paymentservice.dto.BookingResponseDto;
import com.salah.paymentservice.dto.EmailRequestDto;
import com.salah.paymentservice.dto.PaymentRequestDto;
import com.salah.paymentservice.dto.PaymentResponseDto;
import com.salah.paymentservice.mapper.PaymentMapper;
import com.salah.paymentservice.model.Payment;
import com.salah.paymentservice.model.PaymentStatus;
import com.salah.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private BookingClient bookingClient;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto request) {
        // 1️⃣ 调用 Booking Service，获取 Booking 信息
        BookingResponseDto booking = bookingClient.getBookingById(request.bookingId());

        // 2️⃣ 校验 Booking 是否存在
        if (booking == null) {
            throw new RuntimeException("Booking not found with ID: " + request.bookingId());
        }

        // 3️⃣ 校验 Booking 状态
        if (!"PENDING".equalsIgnoreCase(booking.status())) {
            throw new RuntimeException("Cannot pay for booking with status: " + booking.status());
        }

        // 4️⃣ 创建 Payment 实体
        Payment payment = Payment.builder()
                .bookingId(booking.bookingId())
                .amount(booking.totalPrice())          // Obtenir le montant de la réservation
                .status(PaymentStatus.SUCCESS)         // Assurer la réussite d'un prélèvement automatique
                .build();

        // 5️⃣ 保存 Payment
        Payment saved = paymentRepository.save(payment);

        // 🟢 这里更新 Booking 状态
        bookingClient.updateBookingStatus(booking.bookingId(), "CONFIRMED");

        // 📨 Envoi de l’email
        notificationClient.sendEmail(new EmailRequestDto(
                booking.email(), // ou booking.getEmail()
                "Confirmation de votre réservation ✈️",
                "Bonjour " + booking.firstName() + ",\n\nVotre paiement de " + booking.totalPrice() + "€ a été reçu. Votre réservation est confirmée.\n\nMerci."
        ));


        // 6️⃣ 返回 Response
        return paymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponseDto getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }
}
