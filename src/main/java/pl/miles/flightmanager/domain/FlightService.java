package pl.miles.flightmanager.domain;

import pl.miles.flightmanager.domain.dto.AirportInfoDto;
import pl.miles.flightmanager.domain.dto.FlightInfoDto;
import pl.miles.flightmanager.domain.entity.WeightUnit;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface FlightService {

    Optional<FlightInfoDto> getFlightWeightInfo(int flightNumber, ZonedDateTime date, WeightUnit weightUnit);

    Optional<AirportInfoDto> getAirportInfo(String iataCode, ZonedDateTime date);

}
