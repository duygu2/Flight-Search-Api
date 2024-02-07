package com.example.flightSearchApi.controller;

import com.example.flightSearchApi.dto.AirportUpdateDto;
import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAllAirport(){
        return ResponseEntity.ok(airportService.getAllAirport());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAirportById(@PathVariable Long id){
        return ResponseEntity.ok(airportService.getAirportById(id));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping()
    public ResponseEntity<Airport> addAirport(@RequestBody Airport airport){
        return ResponseEntity.ok(airportService.addAirport(airport));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id){
        airportService.deleteAirport(id);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody AirportUpdateDto airport){
        return ResponseEntity.ok(airportService.updateAirport(id,airport));
    }
}
