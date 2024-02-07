package com.example.flightSearchApi.controller;

import com.example.flightSearchApi.dto.FlightCreateDto;
import com.example.flightSearchApi.dto.FlightRequestDto;
import com.example.flightSearchApi.dto.FlightUpdateDto;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @GetMapping("/search")
    public ResponseEntity<Object> searchFlights(
            @RequestParam String departureAirportName,
            @RequestParam String arrivalAirportName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime departureDateTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime returnDateTime) {
        try {
            List<Flight> result = flightService.searchFlights(departureAirportName, arrivalAirportName, departureDateTime, returnDateTime);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
