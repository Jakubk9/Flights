package com.s4a.flights.flight.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {
    private int flightsDeparting;
    private int flightsArriving;
    private int baggageArriving;
    private int baggageDeparting;
}
