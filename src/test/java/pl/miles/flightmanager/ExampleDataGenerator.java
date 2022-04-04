package pl.miles.flightmanager;

import pl.miles.flightmanager.domain.entity.CargoRecord;
import pl.miles.flightmanager.domain.entity.FlightEntity;
import pl.miles.flightmanager.domain.entity.WeightUnit;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExampleDataGenerator {

    static String[] IATA_CODES = new String[]{"GDN", "KRK", "ABC", "QWE", "QAZ", "XSW", "EDC"};

    /**
     * build list with random {@link CargoRecord} objects for testing purpose
     *
     * @param i number of examples in the list
     * @return list of examples
     */
    public static List<CargoRecord> getExampleOfCargoRecords(int i) {
        List<CargoRecord> cargoRecords = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            Random random = new Random();
            int weight = random.nextInt(50 - 1) + 1;
            WeightUnit lb = random.nextBoolean() ? WeightUnit.LB : WeightUnit.KG;
            int pieces = random.nextInt(10 - 1) + 1;
            cargoRecords.add(new CargoRecord(i, weight, lb, pieces));
        }

        return cargoRecords;
    }

    /**
     * build list with random flight entities
     *
     * @param i number of elements
     * @return flight entity list
     */
    public static List<FlightEntity> getExampleOfFlights(int i) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            Random random = new Random();
            int flightId = random.nextInt(50 - 1) + 1;
            int flightNumber = random.nextInt(50 - 1) + 1;

            String departureIataCode = IATA_CODES[random.nextInt(IATA_CODES.length - 1)];
            String arrivalIataCode = IATA_CODES[random.nextInt(IATA_CODES.length - 1)];
            while (departureIataCode.equals(arrivalIataCode)) {
                arrivalIataCode = IATA_CODES[random.nextInt(IATA_CODES.length - 1)];
            }
            ZonedDateTime date = ZonedDateTime.now();
            flightEntities.add(new FlightEntity(flightId, flightNumber, departureIataCode, arrivalIataCode, date));
        }
        return flightEntities;
    }
}
