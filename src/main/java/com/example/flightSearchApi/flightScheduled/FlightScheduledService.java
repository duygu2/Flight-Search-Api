package com.example.flightSearchApi.flightScheduled;

import com.example.flightSearchApi.dto.FlightRequestDto;
import com.example.flightSearchApi.model.Airport;
import com.example.flightSearchApi.model.Flight;
import com.example.flightSearchApi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class FlightScheduledService {

    private final FlightService flightService;


    public FlightScheduledService(FlightService flightService) {
        this.flightService = flightService;
    }

    @Scheduled(cron = "0 30 20 * * *") // Her gün gece yarısı çalıştır
    public void fetchAndSaveFlightDataDaily() {
        // Mock API'den uçuş verilerini al
        List<Flight> flightData = generateMockFlightData();

        // Convert Flight to FlightRequestDto
        List<FlightRequestDto> flightDtoList = convertToFlightRequestDtoList(flightData);

        // Uçuş verilerini veritabanına kaydet
        for (FlightRequestDto flightDto : flightDtoList) {
            flightService.addFlight(flightDto);
        }
    }

    private List<FlightRequestDto> convertToFlightRequestDtoList(List<Flight> flights) {
        List<FlightRequestDto> flightRequestDtos = new ArrayList<>();
        for (Flight flight : flights) {
            FlightRequestDto flightRequestDto = convertToFlightRequestDto(flight);
            flightRequestDtos.add(flightRequestDto);
        }
        return flightRequestDtos;
    }

    private FlightRequestDto convertToFlightRequestDto(Flight flight) {
        FlightRequestDto flightRequestDto = new FlightRequestDto();
        // Set attributes based on Flight attributes
        flightRequestDto.setDepartureDateHour(flight.getDepartureDateHour());
        flightRequestDto.setArrivalDateHour(flight.getArrivalDateHour());
        // Set Departure Airport ID
        Long departureAirportId = flight.getDepartureAirport().getId();
        flightRequestDto.setDepartureAirport(departureAirportId);

        // Set Arrival Airport ID
        Long arrivalAirportId = flight.getArrivalAirport().getId();
        flightRequestDto.setArrivalAirport(arrivalAirportId);
        flightRequestDto.setPrice(flight.getPrice());
        // Set other attributes as needed
        return flightRequestDto;
    }

    private List<Flight> generateMockFlightData() {
        List<Flight> flights = new ArrayList<>();

        // İstanbul'dan Ankara'ya bir uçuş
        Flight flight1 = new Flight();
        flight1.setDepartureDateHour(LocalDateTime.now());
        flight1.setArrivalDateHour(LocalDateTime.now().plusHours(2));
        flight1.setDepartureAirport(createAirport(1L, "İSTANBUL"));
        flight1.setArrivalAirport(createAirport(2L, "ANKARA"));
        flight1.setPrice(250.0);
        flights.add(flight1);

        // Ankara'dan İstanbul'a bir uçuş
        Flight flight2 = new Flight();
        flight2.setDepartureDateHour(LocalDateTime.now().plusDays(1));
        flight2.setArrivalDateHour(LocalDateTime.now().plusDays(1).plusHours(2));
        flight2.setDepartureAirport(createAirport(2L, "ANKARA"));
        flight2.setArrivalAirport(createAirport(1L, "ISTANBUL"));
        flight2.setPrice(180.0);
        flights.add(flight2);

        // Konya'dan İstanbul'a bir uçuş
        Flight flight3 = new Flight();
        flight3.setDepartureDateHour(LocalDateTime.now().plusDays(2));
        flight3.setArrivalDateHour(LocalDateTime.now().plusDays(2).plusHours(3));
        flight3.setDepartureAirport(createAirport(52L, "KONYA"));
        flight3.setArrivalAirport(createAirport(1L, "ISTANBUL"));
        flight3.setPrice(300.0);
        flights.add(flight3);

        // Diğer uçuşlar için aynı şekilde devam edebilirsiniz

        return flights;
    }

    private Airport createAirport(Long id, String code) {
        Airport airport = new Airport();
        airport.setId(id);
        airport.setName(code);
        return airport;
    }
}
