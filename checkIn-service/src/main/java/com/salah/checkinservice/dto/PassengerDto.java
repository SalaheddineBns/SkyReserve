package com.salah.checkinservice.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private Long passengerId;
    private String firstName;
    private String lastName;
    List<BaggageOption> baggageOptions;
}