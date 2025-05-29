package com.salah.bookingservice.dto;

public record SeatReleaseRequestDto(Long flightId,
                                    int seats) { }
