package com.salah.identityservice.dto;


import com.salah.identityservice.model.Role;

public record RegisterResponseDto(Long id, String email, Role role) {}
