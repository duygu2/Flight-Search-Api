package com.example.flightSearchApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDateHour;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalDateHour;


 //   @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Airport departureAirport;

    @OneToOne(cascade = CascadeType.ALL)
    private Airport arrivalAirport;

    private double price;
}
