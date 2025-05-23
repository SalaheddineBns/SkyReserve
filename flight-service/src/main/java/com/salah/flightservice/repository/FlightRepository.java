package com.salah.flightservice.repository;

import com.salah.flightservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginAndDestination(String origin, String destination);

    //Tu peux passer aucun, un, deux ou tous les paramètres, et la requête va s'adapter dynamiquement.
//    @Query("SELECT f FROM Flight f WHERE " +
//            "(:departureCity IS NULL OR f.origin = :origin) AND " +
//            "(:arrivalCity IS NULL OR f.destination = :destination) AND " +
//            "(:date IS NULL OR DATE(f.departureTime) = :date)")
    @Query("""
    SELECT f FROM Flight f WHERE
    (:departureCity IS NULL OR f.origin= :departureCity)
    AND (:arrivalCity IS NULL OR f.destination = :arrivalCity)
    AND (:startDate IS NULL OR f.departureTime >= :startDate)
    AND (:endDate IS NULL OR f.departureTime < :endDate)
""")
    List<Flight> searchFlights(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
