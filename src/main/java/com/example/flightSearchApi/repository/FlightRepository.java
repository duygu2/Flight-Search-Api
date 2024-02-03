package com.example.flightSearchApi.repository;

import com.example.flightSearchApi.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight,Long> {
}
