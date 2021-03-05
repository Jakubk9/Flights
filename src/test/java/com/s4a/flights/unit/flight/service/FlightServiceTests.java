package com.s4a.flights.unit.flight.service;

import com.s4a.flights.cargo.model.Baggage;
import com.s4a.flights.cargo.model.Cargo;
import com.s4a.flights.cargo.model.CargoEntity;
import com.s4a.flights.cargo.model.WeightUnit;
import com.s4a.flights.flight.exception.FlightNotFoundException;
import com.s4a.flights.flight.exception.IATAAirportCodeNotFoundedException;
import com.s4a.flights.flight.model.FlightEntity;
import com.s4a.flights.flight.repository.FlightRepository;
import com.s4a.flights.flight.response.CargoResponse;
import com.s4a.flights.flight.response.FlightResponse;
import com.s4a.flights.flight.service.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class FlightServiceTests {

    private FlightRepository flightRepository;

    private FlightServiceImpl flightService;

    @BeforeEach
    public void setUp() {
        flightRepository = mock(FlightRepository.class);
        flightService = new FlightServiceImpl(flightRepository);
    }

    @Test
    public void getCargoTest() {
        final Date date = new Date();
        final CargoEntity cargoEntity = new CargoEntity();
        final FlightEntity flightEntity = new FlightEntity(1L,"AB111", "LAX", "GDN" , date, cargoEntity);
        final Baggage baggage = new Baggage(1L, 2321, WeightUnit.KG, 321,cargoEntity );
        final Cargo cargo = new Cargo(1L, 2321, WeightUnit.KG, 321,cargoEntity );
        cargoEntity.setId(1L);
        cargoEntity.setBaggage(List.of(baggage));
        cargoEntity.setCargos(List.of(cargo));
        cargoEntity.setFlight(flightEntity);

        when(flightRepository.findByFlightNumberAndDepartureDate("AB111", date)).thenReturn(Optional.of(flightEntity));

        CargoResponse cargoResponse = flightService.getCargo("AB111", date);

        assertThat(cargoResponse.getFlightNumber(), is(flightEntity.getFlightNumber()));
        assertThat(cargoResponse.getBaggageWeight(), is((double)baggage.getWeight()));
        assertThat(cargoResponse.getCargoWeight(), is((double)cargo.getWeight()));
        assertThat(cargoResponse.getTotalWeight(), is((double)(cargo.getWeight() + baggage.getWeight())));
    }

    @Test
    public void givenNonExistingFlightNumberShouldThrowException() {
        final Date date = new Date();
        final String flightNumber = "AB123";

        final FlightNotFoundException result =
                assertThrows(FlightNotFoundException.class, () -> flightService.getCargo(flightNumber, date));

        assertThat(result.getMessage(), is("Given flight number: " + flightNumber + " not Founded"));
    }

    @Test
    public void getFlightsTest() {
        final Date date = new Date();
        final CargoEntity cargoEntity = new CargoEntity();
        final FlightEntity flightEntity = new FlightEntity(1L,"AB111", "LAX", "GDN" , date, cargoEntity);
        final Baggage baggage = new Baggage(1L, 2321, WeightUnit.KG, 321,cargoEntity );
        cargoEntity.setId(1L);
        cargoEntity.setBaggage(List.of(baggage));
        cargoEntity.setFlight(flightEntity);

        when(flightRepository.findAllByArrivalAirportIATACodeAndDepartureDate("LAX", date)).thenReturn(Optional.of(List.of(flightEntity)));
        when(flightRepository.findAllByDepartureAirportIATACodeAndDepartureDate("LAX", date)).thenReturn(Optional.of(List.of(flightEntity)));

        FlightResponse flightResponse = flightService.getFlights("LAX", date);

        assertThat(flightResponse.getFlightsDeparting(), is(1));
        assertThat(flightResponse.getFlightsArriving(), is(1));
        assertThat(flightResponse.getBaggageDeparting(), is(baggage.getPieces()));
        assertThat(flightResponse.getBaggageArriving(), is(baggage.getPieces()));
    }

    @Test
    public void givenNonExistingIATACodeShouldThrowException() {
        final Date date = new Date();
        final String code = "LAX";

        final IATAAirportCodeNotFoundedException result =
                assertThrows(IATAAirportCodeNotFoundedException.class, () -> flightService.getFlights(code, date));

        assertThat(result.getMessage(), is("Given flight number: " + code + " not founded"));
    }
}
