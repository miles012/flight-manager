package pl.miles.flightmanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.miles.flightmanager.ExampleDataGenerator;
import pl.miles.flightmanager.api.FlightController;
import pl.miles.flightmanager.api.FlightNotFoundException;
import pl.miles.flightmanager.domain.dto.AirportInfoDto;
import pl.miles.flightmanager.domain.dto.FlightInfoDto;
import pl.miles.flightmanager.domain.entity.CargoEntity;
import pl.miles.flightmanager.domain.entity.CargoRecord;
import pl.miles.flightmanager.domain.entity.FlightEntity;
import pl.miles.flightmanager.domain.entity.WeightUnit;
import pl.miles.flightmanager.repository.CargoRepository;
import pl.miles.flightmanager.repository.FlightRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class FlightServiceTest {

    @Mock
    CargoRepository cargoRepository;

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    FlightServiceImpl flightService;

    @InjectMocks
    FlightController flightController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightService = new FlightServiceImpl(flightRepository, cargoRepository);
        flightController = new FlightController(flightService);
    }

    @Test
    void when_no_flights_return_empty_data() {
        ZonedDateTime now = ZonedDateTime.now();
        when(flightRepository.findByFlightNumberAndDepartureDate(1, now))
                .thenReturn(Optional.empty());

        Optional<FlightInfoDto> flightWeightInfo = flightService.getFlightWeightInfo(1, now, WeightUnit.KG);

        assertThat(flightWeightInfo).isEmpty();
    }

    @Test
    void when_several_flights_then_weight_is_correct() {
        ZonedDateTime now = ZonedDateTime.now();
        when(flightRepository.findByFlightNumberAndDepartureDate(1, now))
                .thenReturn(Optional.of(new FlightEntity(1, 2, "GDN", "KRK", now)));

        List<CargoRecord> baggage = ExampleDataGenerator.getExampleOfCargoRecords(4);
        List<CargoRecord> cargo = ExampleDataGenerator.getExampleOfCargoRecords(3);

        when(cargoRepository.findByFlightId(1))
                .thenReturn(Optional.of(new CargoEntity(1, baggage, cargo)));

        Optional<FlightInfoDto> flightWeightInfo = flightService.getFlightWeightInfo(1, now, WeightUnit.KG);

        assertThat(flightWeightInfo).isPresent();
        assertThat(flightWeightInfo.get().getTotalWeight())
                .isEqualTo(flightWeightInfo.get().getCargoWeight() + flightWeightInfo.get().getBaggageWeight());

    }

    @Test
    void when_wrong_iata_code_then_exception_thrown() {
        ZonedDateTime now = ZonedDateTime.now();
        String iataCode = "XXX";
        when(flightRepository.findByArrivalAirportIATACodeAndDepartureDate(iataCode, now))
                .thenReturn(Collections.emptyList());

        when(flightRepository.findByDepartureAirportIATACodeAndDepartureDate(iataCode, now))
                .thenReturn(Collections.emptyList());

        assertThrowsExactly(FlightNotFoundException.class, () -> flightController.airportInfo(iataCode, now.toLocalDate()));
    }

    @Test
    void when_several_flight_then_check_counting() {
        LocalDateTime localDate = LocalDateTime.now(ZoneId.of("UTC"));
        String iataCode = "XXX";
        when(flightRepository.findByArrivalAirportIATACodeAndDepartureDate(iataCode, ZonedDateTime.of(localDate, ZoneId.of("UTC"))))
                .thenReturn(ExampleDataGenerator.getExampleOfFlights(10));

        when(flightRepository.findByDepartureAirportIATACodeAndDepartureDate(iataCode, ZonedDateTime.of(localDate, ZoneId.of("UTC"))))
                .thenReturn(ExampleDataGenerator.getExampleOfFlights(20));

        Optional<AirportInfoDto> airportInfo = flightService.getAirportInfo(iataCode, ZonedDateTime.of(localDate, ZoneId.of("UTC")));


        assertThat(airportInfo).isPresent();
        assertThat(airportInfo.get()).hasNoNullFieldsOrProperties();
    }
}