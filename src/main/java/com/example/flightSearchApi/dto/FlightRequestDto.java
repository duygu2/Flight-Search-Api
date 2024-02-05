package com.example.flightSearchApi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequestDto {
    private LocalDateTime departureDateHour;
    private LocalDateTime arrivalDateHour;
    private Long departureAirport;
    private Long arrivalAirport;
    private double price;
}
