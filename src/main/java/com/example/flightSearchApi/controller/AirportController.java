package com.example.flightSearchApi.controller;

import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airport")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllAirport(){
        return ResponseEntity.ok(airportService.getAllAirport());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAirportById(@PathVariable Long id){
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id){
        airportService.deleteAirport(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airport){
        return ResponseEntity.ok(airportService.updateAirport(id,airport));
    }
}
