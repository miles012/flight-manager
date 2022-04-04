package pl.miles.flightmanager.domain;

import pl.miles.flightmanager.domain.dto.AirportInfoDto;
import pl.miles.flightmanager.domain.dto.FlightInfoDto;
import pl.miles.flightmanager.domain.entity.WeightUnit;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface FlightService {

    /**
     *
     * @param flightNumber flight number
     * @param date date of flight presented in UTC time-zone
     * @param weightUnit KG or LB - results will be recalculated
     * @return information about cargo weight for specific flight
     */
    Optional<FlightInfoDto> getFlightWeightInfo(int flightNumber, ZonedDateTime date, WeightUnit weightUnit);

    /**
     *
     * @param iataCode iata code from offical IATA dictionary
     * @param date date of flight presented in UTC time-zone
     * @return information about flights to and from specific airport
     */
    Optional<AirportInfoDto> getAirportInfo(String iataCode, ZonedDateTime date);

}
