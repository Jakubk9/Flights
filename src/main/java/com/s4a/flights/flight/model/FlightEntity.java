package com.s4a.flights.flight.model;

import com.s4a.flights.cargo.model.CargoEntity;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "flights")
public class FlightEntity {
    @Id
    @Column(name = "flight_id")
    private Long id;

    @NotNull
    @NotBlank
    private String flightNumber;

    @NotNull
    @NotBlank
    private String departureAirportIATACode;

    @NotNull
    @NotBlank
    private String arrivalAirportIATACode;

    @NotNull
    @NotBlank
    @Temporal(TemporalType.DATE)
    private Date departureDate;

    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL)
    private CargoEntity cargo;
}
