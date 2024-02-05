package com.example.flightSearchApi.dto;

import com.example.flightSearchApi.model.Airport;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class FlightCreateDto {
    private Airport arrivalAirport;
    private Airport departureAirport;
    private LocalDateTime arrivalDateHour;
    private LocalDateTime departureDateHour;
    private double price;



}
