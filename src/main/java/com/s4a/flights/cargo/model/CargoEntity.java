package com.s4a.flights.cargo.model;

import com.s4a.flights.flight.model.FlightEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cargoes")
public class CargoEntity {
    @Id
    @Column(name = "cargoentity_id")
    private Long id;

    @OneToMany(mappedBy = "cargoEntity", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Baggage> baggage = new ArrayList<>();

    @OneToMany(mappedBy = "cargoEntity", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Cargo> cargos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;
}
