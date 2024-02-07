package com.example.flightSearchApi.repository;

import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    List<Flight> findAllByDepartureAirportAndArrivalAirportAndDepartureDateHourAfter(
            Airport departureAirport, Airport arrivalAirport, LocalDateTime departureDateTime);

    Optional<Flight> findFirstByDepartureAirportAndArrivalAirportAndDepartureDateHourAfter(Airport departureAirport, Airport arrivalAirport, LocalDateTime departureDateTime);
}
