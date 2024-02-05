package com.example.flightSearchApi.service;

import com.example.flightSearchApi.dto.FlightCreateDto;
import com.example.flightSearchApi.dto.FlightRequestDto;
import com.example.flightSearchApi.dto.FlightUpdateDto;
import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.repository.AirportRepository;
import com.example.flightSearchApi.repository.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public List<Flight> getAllFlight(){
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id){
        return flightRepository.findById(id);
    }

    public Flight addFlight(FlightRequestDto requestDto){

        Flight flight = new Flight();

        Optional<Airport> optionalDepartureAirport = airportRepository.findById(requestDto.getDepartureAirport());
        optionalDepartureAirport.ifPresentOrElse(
                departureAirport -> flight.setDepartureAirport(departureAirport),
                () -> {
                    throw new IllegalStateException("Departure Airport not found with id: " + requestDto.getDepartureAirport());
                }
        );

        Optional<Airport> optionalArrivalAirport = airportRepository.findById(requestDto.getArrivalAirport());
        optionalArrivalAirport.ifPresentOrElse(
                arrivalAirport -> flight.setArrivalAirport(arrivalAirport),
                () -> {
                    throw new IllegalStateException("Arrival Airport not found with id: " + requestDto.getArrivalAirport());
                }
        );

        flight.setDepartureDateHour(requestDto.getDepartureDateHour());
        flight.setArrivalDateHour(requestDto.getArrivalDateHour());
        flight.setPrice(requestDto.getPrice());

        return flightRepository.save(flight);




        /*Airport departureAirport= flight.getDepartureAirport();
        Airport arrivalAirport= flight.getArrivalAirport();

        if(departureAirport!=null && arrivalAirport!=null && departureAirport.getId() !=null && arrivalAirport.getId() !=null){
            departureAirport=airportRepository.getOne(departureAirport.getId());
            arrivalAirport=airportRepository.getOne(arrivalAirport.getId());

            flight.setDepartureAirport(departureAirport);
            flight.setArrivalAirport(arrivalAirport);

            return flightRepository.save(flight);
        }else {
            throw new RuntimeException("Please, Identify Departure Airport and Arrival Airport ");
        }*/
    }

    public Flight updateFlight(Long id, FlightRequestDto requestDto){
        Optional<Flight> optionalFlight = flightRepository.findById(id);

        return optionalFlight.map(flight -> {
            Optional<Airport> optionalDepartureAirport = airportRepository.findById(requestDto.getDepartureAirport());
            optionalDepartureAirport.ifPresentOrElse(
                    departureAirport -> flight.setDepartureAirport(departureAirport),
                    () -> {
                        throw new IllegalStateException("Departure Airport not found with id: " + requestDto.getDepartureAirport());
                    }
            );

            Optional<Airport> optionalArrivalAirport = airportRepository.findById(requestDto.getArrivalAirport());
            optionalArrivalAirport.ifPresentOrElse(
                    arrivalAirport -> flight.setArrivalAirport(arrivalAirport),
                    () -> {
                        throw new IllegalStateException("Arrival Airport not found with id: " + requestDto.getArrivalAirport());
                    }
            );

            flight.setDepartureDateHour(requestDto.getDepartureDateHour());
            flight.setArrivalDateHour(requestDto.getArrivalDateHour());
            flight.setPrice(requestDto.getPrice());

            // Güncelleme için save metodu kullanılıyor
            return flightRepository.save(flight);
        }).orElseThrow(() -> new RuntimeException("Couldn't find flight with id: " + id));
    }

    public void deleteFlight(Long id){
        flightRepository.deleteById(id);
    }

}


