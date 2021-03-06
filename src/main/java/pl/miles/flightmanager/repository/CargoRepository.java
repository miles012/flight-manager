package pl.miles.flightmanager.repository;

import pl.miles.flightmanager.domain.entity.CargoEntity;

import java.util.Optional;

public interface CargoRepository {

    Optional<CargoEntity> findByFlightId(int flightId);
}
