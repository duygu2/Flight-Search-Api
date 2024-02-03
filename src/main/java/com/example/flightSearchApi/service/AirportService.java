package com.example.flightSearchApi.service;

import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;


    public List<Airport> getAllAirport() {
        return airportRepository.findAll();
    }

    public Optional<Airport> getAirportById(Long id){
        return airportRepository.findById(id);
    }

    public Airport addAirport(Airport airport){
        return airportRepository.save(airport);
    }

    public Airport updateAirport(Long id, Airport airport){
        return airportRepository.findById(id)
                .map(airport1 -> {
                    airport1.setName(airport.getName());
                    return airportRepository.save(airport1);
                })
                .orElseGet(()-> airportRepository.save(airport));

    }

   // public Collection<Flight> flights   tüm uçuşları getirme böyle mi olacak acaba

    public void deleteAirport(Long id){
        airportRepository.deleteById(id);
    }


}
