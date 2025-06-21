package com.salah.inventoryservice.service;

import com.salah.inventoryservice.model.Inventory;
import com.salah.inventoryservice.model.Seat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    public List<Seat> generateSeats(Inventory inventory, int totalSeats) {
        List<Seat> seats = new ArrayList<>();
        char[] seatLetters = {'A', 'B', 'C', 'D', 'E', 'F'};

        int row = 1;
        int index = 0;

        for (int i = 0; i < totalSeats; i++) {
            String seatNumber = row + "" + seatLetters[index];
            seats.add(Seat.builder()
                    .seatNumber(seatNumber)
                    .isAvailable(true)
                    .inventory(inventory)  // ✅ on passe l'objet Inventory
                    .build());

            index++;
            if (index >= seatLetters.length) {
                index = 0;
                row++;
            }
        }

        return seats;
    }
}
