package com.s4a.flights.flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FlightExceptionHandler {
    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getWrongFlightNumberOrDateException(FlightNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IATAAirportCodeNotFoundedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getWrongIATAAirportCodeOrDateException(IATAAirportCodeNotFoundedException ex) {
        return ex.getMessage();
    }
}
