package com.example.flightSearchApi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(mappedBy ="departureAirport" )
    private List<Flight> departureAirportList= new ArrayList<>();

    @OneToMany(mappedBy ="arrivalAirport" )
    private List<Flight> arrivalAirportList= new ArrayList<>();


}
