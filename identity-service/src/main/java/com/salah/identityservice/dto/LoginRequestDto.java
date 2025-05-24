package com.salah.identityservice.dto;

import lombok.Data;


public record LoginRequestDto (
     String email,
     String password
){}

