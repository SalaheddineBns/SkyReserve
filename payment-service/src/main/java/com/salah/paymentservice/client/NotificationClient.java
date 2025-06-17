package com.salah.paymentservice.client;

import com.salah.paymentservice.dto.EmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "http://localhost:8088")
public interface NotificationClient {

    @PostMapping("/api/notifications/send")
    void sendEmail(@RequestBody EmailRequestDto request);
}
