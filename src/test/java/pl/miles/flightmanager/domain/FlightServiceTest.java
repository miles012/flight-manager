package pl.miles.flightmanager.domain;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.miles.flightmanager.api.FlightController;
import pl.miles.flightmanager.api.FlightNotFoundException;
import pl.miles.flightmanager.api.GlobalExceptionHandler;
import pl.miles.flightmanager.domain.dto.AirportInfoDto;
import pl.miles.flightmanager.domain.dto.FlightInfoDto;
import pl.miles.flightmanager.domain.entity.CargoEntity;
import pl.miles.flightmanager.domain.entity.CargoRecord;
import pl.miles.flightmanager.domain.entity.FlightEntity;
import pl.miles.flightmanager.domain.entity.WeightUnit;
import pl.miles.flightmanager.repository.CargoRepository;
import pl.miles.flightmanager.repository.FlightRepository;

import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
                .thenReturn(Optional.of(new FlightEntity(1,2,"GDN","KRK",now)));

        List<CargoRecord> baggage = getExampleOfBaggage(4);
        List<CargoRecord> cargo = getExampleOfBaggage(3);

        when(cargoRepository.findByFlightId(1))
                .thenReturn(Optional.of(new CargoEntity(1, baggage, cargo)));

        Optional<FlightInfoDto> flightWeightInfo = flightService.getFlightWeightInfo(1, now, WeightUnit.KG);

        assertThat(flightWeightInfo).isPresent();
        assertThat(flightWeightInfo.get().getTotalWeight())
                .isEqualTo(flightWeightInfo.get().getCargoWeight() + flightWeightInfo.get().getBaggageWeight());

    }

    @Test
    void when_wrond_iata_code_then_exception_thrown() {
        ZonedDateTime now = ZonedDateTime.now();
        String iataCode = "XXX";
        when(flightRepository.findByArrivalAirportIATACodeAndDepartureDate(iataCode, now))
                .thenReturn(Collections.emptyList());

        when(flightRepository.findByDepartureAirportIATACodeAndDepartureDate(iataCode, now))
                .thenReturn(Collections.emptyList());

        assertThrowsExactly(FlightNotFoundException.class, () -> flightController.airportInfo(iataCode, now.toLocalDate()));
    }

    private List<CargoRecord> getExampleOfBaggage(int i) {
        List<CargoRecord> baggage = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            Random random = new Random();
            int weight = random.nextInt(50 - 1) + 1;
            WeightUnit lb = random.nextBoolean() ? WeightUnit.LB : WeightUnit.KG;
            int pieces = random.nextInt(10 - 1) + 1;
            baggage.add(new CargoRecord(i, weight, lb, pieces));
        }

        return baggage;
    }
}