package com.s4a.flights.flight.exception;

public class IATAAirportCodeNotFoundedException extends RuntimeException{
    public IATAAirportCodeNotFoundedException(String code) { super("Given flight number: " + code + " not founded");}

}
