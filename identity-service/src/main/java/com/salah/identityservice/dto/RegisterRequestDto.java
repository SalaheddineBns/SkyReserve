package com.salah.identityservice.dto;


import com.salah.identityservice.model.Role;

public record RegisterRequestDto (String email,
                                  String password,
                                  Role role){}

