package com.salah.identityservice.controller;

import com.salah.identityservice.dto.*;
import com.salah.identityservice.service.AuthService;
import com.salah.identityservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
        RegisterResponseDto userDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

   @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
       LoginResponseDto response = authService.login(request);
       return ResponseEntity.ok(response);
  }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(customUserDetailsService.getAllUsers());
    }
}

