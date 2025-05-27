package com.salah.inventoryservice.service;

import com.salah.inventoryservice.model.Seat;
import com.salah.inventoryservice.model.enums.SeatClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    public List<Seat> generateSeats(Long inventoryId, int totalSeats) {
        List<Seat> seats = new ArrayList<>();
        char[] seatLetters = {'A', 'B', 'C', 'D', 'E', 'F'};

        int row = 1;
        int index = 0;

        for (int i = 0; i < totalSeats; i++) {
            String seatNumber = row + "" + seatLetters[index];
            seats.add(Seat.builder()
                    .inventoryId(inventoryId)
                    .seatNumber(seatNumber)
                    .seatClass(SeatClass.ECONOMY) // Par défaut, on peut améliorer ça plus tard
                    .reserved(false)
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
