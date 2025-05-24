package com.salah.identityservice.dto;


public record UserDto(
        Long userId,
        String email,
        String role // Ex: USER, ADMIN
 ){}
