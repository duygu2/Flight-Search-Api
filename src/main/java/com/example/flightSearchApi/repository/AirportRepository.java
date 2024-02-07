package com.example.flightSearchApi.repository;

import com.example.flightSearchApi.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport,Long> {
    Airport findByName(String departureAirportName);
}
