package com.s4a.flights.flight.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoResponse {
    private String flightNumber;
    private double cargoWeight;
    private double baggageWeight;
    private double totalWeight;
}
