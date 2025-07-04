package com.salah.identityservice.dto;


import com.salah.identityservice.model.Role;

public record UserDto(
        Long userId,
        String email,
        Role role // Ex: USER, ADMIN
 ){}
