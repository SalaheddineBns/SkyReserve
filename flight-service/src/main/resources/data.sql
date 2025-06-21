INSERT INTO flight (flight_number, origin, destination, departure_time, arrival_time, price, aircraft_id) VALUES
                                                                                                              -- Paris -> diverses destinations
                                                                                                              ('AF123', 'Paris', 'New York', '2025-06-22T10:00:00', '2025-06-01T13:00:00', 750.00, 1),
                                                                                                              ('AF125', 'Paris', 'Brussels', '2025-06-22T10:00:00', '2025-06-01T11:15:00', 350.00, 1),
                                                                                                              ('AF126', 'Paris', 'Madrid', '2025-06-02T12:00:00', '2025-06-02T14:00:00', 400.00, 1),
                                                                                                              ('AF127', 'Paris', 'Rome', '2025-06-02T15:00:00', '2025-06-02T17:30:00', 450.00, 1),

                                                                                                              -- London -> diverses destinations
                                                                                                              ('BA456', 'London', 'Tokyo', '2025-06-02T15:30:00', '2025-06-03T07:45:00', 1200.00, 2),
                                                                                                              ('BA457', 'London', 'Paris', '2025-06-02T09:00:00', '2025-06-02T11:00:00', 380.00, 2),
                                                                                                              ('BA458', 'London', 'New York', '2025-06-03T14:00:00', '2025-06-03T20:00:00', 980.00, 2),

                                                                                                              -- Berlin -> diverses destinations
                                                                                                              ('LH789', 'Berlin', 'Dubai', '2025-06-19T08:00:00', '2025-06-05T16:30:00', 680.00, 2),
                                                                                                              ('LH790', 'Berlin', 'Rome', '2025-06-05T10:30:00', '2025-06-05T12:30:00', 420.00, 2),
                                                                                                              ('LH791', 'Berlin', 'London', '2025-06-06T13:00:00', '2025-06-06T15:00:00', 350.00, 2),

                                                                                                              -- Dubai -> diverses destinations
                                                                                                              ('EK321', 'Dubai', 'Sydney', '2025-06-07T22:00:00', '2025-06-08T10:15:00', 1400.00, 3),
                                                                                                              ('EK322', 'Dubai', 'Paris', '2025-06-08T09:00:00', '2025-06-08T15:30:00', 1100.00, 3),
                                                                                                              ('EK323', 'Dubai', 'Mumbai', '2025-06-09T07:00:00', '2025-06-09T11:00:00', 600.00, 3),

                                                                                                              -- New York -> diverses destinations
                                                                                                              ('DL987', 'New York', 'Los Angeles', '2025-06-10T07:00:00', '2025-06-10T10:15:00', 350.00, 3),
                                                                                                              ('DL988', 'New York', 'Chicago', '2025-06-10T12:00:00', '2025-06-10T14:30:00', 280.00, 3),
                                                                                                              ('DL989', 'New York', 'Toronto', '2025-06-11T09:00:00', '2025-06-11T11:00:00', 320.00, 3);
