package pl.miles.flightmanager.domain;

import org.springframework.stereotype.Service;
import pl.miles.flightmanager.domain.dto.AirportInfoDto;
import pl.miles.flightmanager.domain.dto.FlightInfoDto;
import pl.miles.flightmanager.domain.entity.CargoRecord;
import pl.miles.flightmanager.domain.entity.FlightEntity;
import pl.miles.flightmanager.domain.entity.WeightUnit;
import pl.miles.flightmanager.repository.CargoRepository;
import pl.miles.flightmanager.repository.FlightRepository;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
class FlightServiceImpl implements FlightService {

    private static final String WEIGHT_DECIMAL_FORMAT = "#0.00";

    private final FlightRepository flightRepository;
    private final CargoRepository cargoRepository;

    public FlightServiceImpl(FlightRepository flightRepository, CargoRepository cargoRepository) {
        this.flightRepository = flightRepository;
        this.cargoRepository = cargoRepository;
    }

    @Override
    public Optional<AirportInfoDto> getAirportInfo(String iataCode, ZonedDateTime date) {
        Objects.requireNonNull(iataCode, "iataCode must not be null");
        Objects.requireNonNull(date, "date must not be null");

        List<FlightEntity> arrivals = flightRepository.findByArrivalAirportIATACodeAndDepartureDate(iataCode, date);
        List<FlightEntity> departures = flightRepository.findByDepartureAirportIATACodeAndDepartureDate(iataCode, date);

        int arrivalsCargo = arrivals.stream()
                .map(FlightEntity::getFlightId)
                .map(cargoRepository::findByFlightId)
                .flatMap(Optional::stream)
                .mapToInt(f -> 1)
                .sum();

        int departuresCargo = departures.stream()
                .map(FlightEntity::getFlightId)
                .map(cargoRepository::findByFlightId)
                .flatMap(Optional::stream)
                .mapToInt(f -> 1)
                .sum();

        if (!arrivals.isEmpty() || !departures.isEmpty()) {
            return Optional.of(new AirportInfoDto.Builder()
                    .numberOfArrivals(arrivals.size())
                    .numberOfDepartures(departures.size())
                    .numberOfBaggageArrivals(arrivalsCargo)
                    .numberOfBaggageDepartures(departuresCargo)
                    .build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<FlightInfoDto> getFlightWeightInfo(int flightNumber, ZonedDateTime date, WeightUnit weightUnit) {
        Objects.requireNonNull(flightNumber, "flightNumber must not be null");
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(weightUnit, "weightUnit must not be null");

        return flightRepository.findByFlightNumberAndDepartureDate(flightNumber, date)
                .map(FlightEntity::getFlightId)
                .flatMap(cargoRepository::findByFlightId)
                .map(cargoEntity -> new FlightInfoDto.Builder()
                        .baggageWeight(calculateWeight(cargoEntity.getBaggage(), weightUnit))
                        .cargoWeight(calculateWeight(cargoEntity.getCargo(), weightUnit))
                        .weightUnit(weightUnit)
                        .build());
    }

    private double calculateWeight(List<CargoRecord> cargoRecords, WeightUnit weightUnit) {
        Double weight = cargoRecords.stream()
                .map(getNormalizedWeight(weightUnit))
                .reduce(0.00, Double::sum);
        DecimalFormat dec = new DecimalFormat(WEIGHT_DECIMAL_FORMAT);
        return Double.parseDouble(dec.format(weight));
    }

    private Function<CargoRecord, Double> getNormalizedWeight(WeightUnit weightUnit) {
        return cargoRecord -> {
            if (cargoRecord.getWeightUnit() == weightUnit) {
                return (double) cargoRecord.getWeight();
            }

            if (cargoRecord.getWeightUnit() == WeightUnit.KG) {
                return WeightUnit.fromKgToLb(cargoRecord.getWeight());
            }

            if (cargoRecord.getWeightUnit() == WeightUnit.LB) {
                return WeightUnit.fromLbToKg(cargoRecord.getWeight());
            }

            throw new IllegalArgumentException("Unhandled weight unit");
        };
    }
}
