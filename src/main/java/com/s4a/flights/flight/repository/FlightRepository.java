package com.s4a.flights.flight.repository;

import com.s4a.flights.flight.model.FlightEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<FlightEntity, Long> {
    Optional<FlightEntity> findByFlightNumberAndDepartureDate(String flightNumber, Date date);

    Optional<List<FlightEntity>> findAllByArrivalAirportIATACodeAndDepartureDate(String code, Date date);

    Optional<List<FlightEntity>> findAllByDepartureAirportIATACodeAndDepartureDate(String code, Date date);

    FlightEntity findByFlightNumber(String flightNumber);
}
