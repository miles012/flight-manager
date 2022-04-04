package pl.miles.flightmanager.repository;

import pl.miles.flightmanager.domain.FlightEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository {

    Optional<FlightEntity> findByFlightNumberAndDepartureDate(int flightNumber, ZonedDateTime date);

    List<FlightEntity> findByDepartureAirportIATACodeAndDepartureDate(String iataCode, ZonedDateTime date);

    List<FlightEntity> findByArrivalAirportIATACodeAndDepartureDate(String iataCode, ZonedDateTime date);

}
