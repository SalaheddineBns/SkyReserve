package com.salah.checkinservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaggageCheckinRequestDto {
    private String firstName;
    private String lastName;
}