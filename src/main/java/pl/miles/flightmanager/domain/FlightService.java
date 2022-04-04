package pl.miles.flightmanager.domain;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface FlightService {

    Optional<FlightInfoDto> getFlightWeightInfo(int flightNumber, ZonedDateTime date, WeightUnit weightUnit);

    Optional<AirportInfoDto> getAirportInfo(String iataCode, ZonedDateTime date);

}
