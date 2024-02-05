package com.example.flightSearchApi.service;

import com.example.flightSearchApi.dto.AirportUpdateDto;
import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public Airport updateAirport(Long id, AirportUpdateDto airportDto){
       Optional<Airport> optionalAirport = airportRepository.findById(id);
       if(optionalAirport.isPresent()){
           Airport updateAirport= optionalAirport.get();
           updateAirport.setName(airportDto.getName());
           return airportRepository.save(updateAirport);
       }
       else{
           throw new RuntimeException("Couldn't find airport.");
       }

    }


    public void deleteAirport(Long id){
        airportRepository.deleteById(id);
    }


}
