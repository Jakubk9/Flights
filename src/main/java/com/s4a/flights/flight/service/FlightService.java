package com.s4a.flights.flight.service;

import com.s4a.flights.flight.response.FlightResponse;
import com.s4a.flights.flight.response.CargoResponse;

import java.util.Date;

public interface FlightService {
    CargoResponse getCargo(String flightNumber, Date date);

    FlightResponse getFlights(String code, Date date);
}
