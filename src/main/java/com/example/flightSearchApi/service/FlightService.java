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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Flight> searchFlights(String departureAirportName, String arrivalAirportName, LocalDateTime departureDateTime, LocalDateTime returnDateTime) {
        try {
            Optional<Airport> departureAirportOptional = Optional.ofNullable(airportRepository.findByName(departureAirportName));
            Airport departureAirport = departureAirportOptional.orElseThrow(() -> new IllegalArgumentException("Departure Airport not found with name: " + departureAirportName));

            Optional<Airport> arrivalAirportOptional = Optional.ofNullable(airportRepository.findByName(arrivalAirportName));
            Airport arrivalAirport = arrivalAirportOptional.orElseThrow(() -> new IllegalArgumentException("Arrival Airport not found with name: " + arrivalAirportName));

            if (returnDateTime != null) {
                // Çift yönlü uçuş
                // Dönüş tarihi verildiyse, iki yönlü uçuşları ara
                List<Flight> flights = flightRepository.findAllByDepartureAirportAndArrivalAirportAndDepartureDateHourAfter(
                        departureAirport, arrivalAirport, departureDateTime);

                if (flights.isEmpty()) {
                    throw new RuntimeException("No round-trip flights found for the specified criteria");
                }

                return flights;
            } else {
                // Tek yönlü uçuş
                // Dönüş tarihi verilmediyse, tek yönlü uçuşları ara
                Optional<Flight> optionalFlight = flightRepository.findFirstByDepartureAirportAndArrivalAirportAndDepartureDateHourAfter(
                        departureAirport, arrivalAirport, departureDateTime);

                return optionalFlight.map(Collections::singletonList)
                        .orElseThrow(() -> new RuntimeException("No one-way flight found for the specified criteria"));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }}

