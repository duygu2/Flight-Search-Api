package com.example.flightSearchApi.controller;

import com.example.flightSearchApi.dto.FlightCreateDto;
import com.example.flightSearchApi.dto.FlightRequestDto;
import com.example.flightSearchApi.dto.FlightUpdateDto;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @GetMapping()
    public ResponseEntity<?> getAllFlights(){
        return  ResponseEntity.ok(flightService.getAllFlight());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable Long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }
    @PostMapping()
    public ResponseEntity<Flight> addFlight(@RequestBody FlightRequestDto flight){
        return ResponseEntity.ok(flightService.addFlight(flight));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id){
        flightService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightRequestDto flightUpdateDto){
        return ResponseEntity.ok(flightService.updateFlight(id,flightUpdateDto));
    }
}
