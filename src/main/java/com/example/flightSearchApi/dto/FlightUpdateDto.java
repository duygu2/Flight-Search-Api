package com.example.flightSearchApi.dto;

import com.example.flightSearchApi.model.Airport;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class FlightUpdateDto {
    private Airport departureAirport;
    private Airport arrivalAirport;
    private double price;
    private LocalDateTime departureDateHour;
    private LocalDateTime arrivalDateHour;
}
