package pl.miles.flightmanager.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import pl.miles.flightmanager.configuration.ResourceFilesUtil;
import pl.miles.flightmanager.configuration.JsonObjectMapper;
import pl.miles.flightmanager.domain.CargoEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Log4j2
class CargoRepositoryImpl implements CargoRepository {

    private static final List<CargoEntity> CARGO_ENTITIES;

    private static final String CARGO_JSON = "cargo.json";

    static {
        String cargoJson = ResourceFilesUtil.readFile(CARGO_JSON);
        List<CargoEntity> cargoEntityList = getCargoEntities(cargoJson);
        CARGO_ENTITIES = Collections.unmodifiableList(cargoEntityList);
        log.debug("Successfully read " + CARGO_ENTITIES.size()  + " cargo entities");
    }

    private static List<CargoEntity> getCargoEntities(String cargoJson) {
        ObjectMapper objectMapper = JsonObjectMapper.jsonMapper();
        try {
            return objectMapper.readValue(cargoJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error occur during deserialize json file", e);
        }
    }

    @Override
    public Optional<CargoEntity> findByFlightId(int flightId) {
        return CARGO_ENTITIES.stream()
                .filter(e -> e.getFlightId() == flightId)
                .findFirst();
    }
}
