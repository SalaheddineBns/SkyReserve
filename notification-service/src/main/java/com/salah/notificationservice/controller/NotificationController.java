package com.salah.notificationservice.controller;

import com.salah.notificationservice.dto.EmailRequestDto;
import com.salah.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequestDto request) {
        emailService.sendSimpleMessage(request.getTo(), request.getSubject(), request.getText());
        return ResponseEntity.ok().build();
    }
}


