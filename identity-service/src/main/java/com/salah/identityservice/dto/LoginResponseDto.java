package com.salah.identityservice.dto;

import com.salah.identityservice.model.Role;

public record LoginResponseDto(String token, String email, Role role) {}
