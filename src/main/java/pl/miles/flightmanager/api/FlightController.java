package pl.miles.flightmanager.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.miles.flightmanager.configuration.FlightNotFoundException;
import pl.miles.flightmanager.domain.AirportInfoDto;
import pl.miles.flightmanager.domain.FlightInfoDto;
import pl.miles.flightmanager.domain.FlightService;
import pl.miles.flightmanager.domain.WeightUnit;

import java.time.LocalDate;

import static pl.miles.flightmanager.api.ApiPaths.AIRPORT_INFO;
import static pl.miles.flightmanager.api.ApiPaths.FLIGHT_INFO;
import static pl.miles.flightmanager.configuration.DefaultParameters.DEFAULT_WEIGHT_UNIT;
import static pl.miles.flightmanager.configuration.DefaultParameters.DEFAULT_ZONE_ID;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(FLIGHT_INFO)
    public ResponseEntity<FlightInfoDto> flightWeightInfo(@Parameter(description = "Flight number") @RequestParam(value = "flightNumber") int flightNumber,
                                                          @Parameter(description = "Date from UTC zone") @RequestParam(value = "date") LocalDate date,
                                                          @Parameter(description = "If no present, KG will be taken") @RequestParam(value = "weightUnit", required = false) WeightUnit weightUnit) {

        WeightUnit finalWeightUnit = weightUnit != null ? weightUnit : DEFAULT_WEIGHT_UNIT;
        return flightService.getFlightWeightInfo(flightNumber, date.atStartOfDay(DEFAULT_ZONE_ID), finalWeightUnit)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FlightNotFoundException("There is no any flights with these parameters"));
    }

    @GetMapping(AIRPORT_INFO)
    @Operation(summary = "Return")
    public ResponseEntity<AirportInfoDto> airportInfo(@Parameter(description = "Airport IATA code") @RequestParam("iataCode") String iataCode,
                                                      @Parameter(description = "Date from UTC zone") @RequestParam("date") LocalDate date) {
        return flightService.getAirportInfo(iataCode, date.atStartOfDay(DEFAULT_ZONE_ID))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FlightNotFoundException("There is no any flights with these parameters"));
    }
}
