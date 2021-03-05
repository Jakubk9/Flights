package com.s4a.flights.flight.controller;

import com.s4a.flights.flight.response.FlightResponse;
import com.s4a.flights.flight.response.CargoResponse;
import com.s4a.flights.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flight/cargo")
    public CargoResponse getCargo(@RequestParam String flightNumber, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return flightService.getCargo(flightNumber, date);
    }

    @GetMapping("/flight")
    public FlightResponse getFlights(@RequestParam String code, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return flightService.getFlights(code, date);
    }
}
