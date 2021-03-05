package com.s4a.flights.cargo.repository;

import com.s4a.flights.cargo.model.CargoEntity;
import org.springframework.data.repository.CrudRepository;

public interface CargoRepository extends CrudRepository<CargoEntity, Long> {
}
