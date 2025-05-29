package com.salah.baggageservice.model.enums;

public enum BaggageType {
    CABIN(10.0, 30.0),   // maxWeight = 10kg, price = 30€
    SOUTE(25.0, 60.0),
    SPECIAL(30.0, 100.0); // exemple

    private final double maxWeight;
    private final double price;

    BaggageType(double maxWeight, double price) {
        this.maxWeight = maxWeight;
        this.price = price;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public double getPrice() {
        return price;
    }
}

