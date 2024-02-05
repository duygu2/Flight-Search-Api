package com.example.flightSearchApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy ="departureAirport" )
    private List<Flight> departureAirportList= new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy ="arrivalAirport" )
    private List<Flight> arrivalAirportList= new ArrayList<>();


}
