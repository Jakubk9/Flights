package com.s4a.flights.flight.service;

import com.s4a.flights.cargo.model.Baggage;
import com.s4a.flights.cargo.model.Cargo;
import com.s4a.flights.cargo.model.CargoEntity;
import com.s4a.flights.cargo.model.WeightUnit;
import com.s4a.flights.flight.exception.FlightNotFoundException;
import com.s4a.flights.flight.exception.IATAAirportCodeNotFoundedException;
import com.s4a.flights.flight.model.FlightEntity;
import com.s4a.flights.flight.repository.FlightRepository;
import com.s4a.flights.flight.response.FlightResponse;
import com.s4a.flights.flight.response.CargoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{
    private final FlightRepository flightRepository;

    private final static double LB_TO_KG = 0.45359237;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public CargoResponse getCargo(String flightNumber, Date date) {
        CargoEntity cargoEntity = flightRepository.findByFlightNumberAndDepartureDate(flightNumber,date).orElseThrow(
                () -> new FlightNotFoundException(flightNumber)
        ).getCargo();
        double baggageWeight = 0;
        List<Baggage> baggage = cargoEntity.getBaggage();
        for (Baggage b : baggage) {
            if (WeightUnit.LB.equals(b.getWeightUnit())) {
                baggageWeight += b.getWeight() * LB_TO_KG;
            } else baggageWeight += b.getWeight();
        }
        double cargoWeight = 0;
        List<Cargo> cargos = cargoEntity.getCargos();
        for (Cargo c : cargos) {
            if (WeightUnit.LB.equals(c.getWeightUnit())) {
                cargoWeight += c.getWeight() * LB_TO_KG;
            } else cargoWeight += c.getWeight();
        }
        double TotalWeight = cargoWeight + baggageWeight;

        return CargoResponse.builder().flightNumber(flightNumber).baggageWeight(baggageWeight).cargoWeight(cargoWeight).totalWeight(TotalWeight).build();
    }

    @Override
    public FlightResponse getFlights(String code, Date date) {
        List<FlightEntity> arrivalFlights = flightRepository.findAllByArrivalAirportIATACodeAndDepartureDate(code, date).orElseThrow(
                () -> new IATAAirportCodeNotFoundedException(code)
        );
        List<FlightEntity> departureFlights = flightRepository.findAllByDepartureAirportIATACodeAndDepartureDate(code, date).orElseThrow(
                () -> new IATAAirportCodeNotFoundedException(code)
        );
        int arrivalBaggage = 0;
        int departureBaggage = 0;
        for (FlightEntity f : arrivalFlights) {
            List<Baggage> baggage = f.getCargo().getBaggage();
            for (Baggage b : baggage) {
                arrivalBaggage += b.getPieces();
            }
        }
        for (FlightEntity f : departureFlights) {
            CargoEntity cargoEntity = f.getCargo();
            List<Baggage> baggage = cargoEntity.getBaggage();
            for (Baggage b : baggage) {
                departureBaggage += b.getPieces();
            }
        }
        return FlightResponse.builder().flightsArriving(arrivalFlights.size()).flightsDeparting(departureFlights.size()).
                baggageArriving(arrivalBaggage).baggageDeparting(departureBaggage).build();
    }
}
