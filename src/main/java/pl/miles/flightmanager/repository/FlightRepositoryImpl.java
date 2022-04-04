package pl.miles.flightmanager.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import pl.miles.flightmanager.configuration.ResourceFilesUtil;
import pl.miles.flightmanager.configuration.JsonObjectMapper;
import pl.miles.flightmanager.domain.entity.FlightEntity;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Log4j2
class FlightRepositoryImpl implements FlightRepository {

    private static final List<FlightEntity> FLIGHT_ENTITIES;

    private static final String FLIGHTS_JSON = "flights.json";

    static {
        String flightsJson = ResourceFilesUtil.readFile(FLIGHTS_JSON);
        List<FlightEntity> flightEntitiesList = getFlightEntities(flightsJson);
        FLIGHT_ENTITIES = Collections.unmodifiableList(flightEntitiesList);
        log.debug("Successfully read " + FLIGHT_ENTITIES.size()  + " flight entities");
    }

    private static List<FlightEntity> getFlightEntities(String flightsJson) {
        ObjectMapper objectMapper = JsonObjectMapper.jsonMapper();
        try {
            return objectMapper.readValue(flightsJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error occur during deserialize json file", e);
        }
    }

    @Override
    public Optional<FlightEntity> findByFlightNumberAndDepartureDate(int flightNumber, ZonedDateTime date) {
        return FLIGHT_ENTITIES.stream()
                .filter(e -> e.getFlightNumber() == flightNumber)
                .filter(e -> e.getDepartureDate().toLocalDate().isEqual(date.toLocalDate()))
                .findFirst();
    }

    @Override
    public List<FlightEntity> findByDepartureAirportIATACodeAndDepartureDate(String iataCode, ZonedDateTime date) {
        return FLIGHT_ENTITIES.stream()
                .filter(e -> e.getDepartureAirportIATACode().equalsIgnoreCase(iataCode))
                .filter(e -> e.getDepartureDate().toLocalDate().isEqual(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightEntity> findByArrivalAirportIATACodeAndDepartureDate(String iataCode, ZonedDateTime date) {
        return FLIGHT_ENTITIES.stream()
                .filter(e -> e.getArrivalAirportIATACode().equalsIgnoreCase(iataCode))
                .filter(e -> e.getDepartureDate().toLocalDate().isEqual(date.toLocalDate()))
                .collect(Collectors.toList());
    }
}
