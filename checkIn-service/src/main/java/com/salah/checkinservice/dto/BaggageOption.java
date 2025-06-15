package com.salah.checkinservice.dto;

import lombok.Data;

@Data
public class BaggageOption {
    private String type; // "CABIN" or "SOUTE"
    private int quantity;
}