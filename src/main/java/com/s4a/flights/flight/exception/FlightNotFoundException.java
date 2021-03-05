package com.s4a.flights.flight.exception;

public class FlightNotFoundException extends RuntimeException{
    public FlightNotFoundException(String flightNumber) { super("Given flight number: " + flightNumber + " not Founded");}
}
