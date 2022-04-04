package pl.miles.flightmanager.domain.entity;

import java.util.Collections;
import java.util.List;

public class CargoEntity {

    private int flightId;
    private List<CargoRecord> baggage;
    private List<CargoRecord> cargo;

    public int getFlightId() {
        return flightId;
    }

    public List<CargoRecord> getBaggage() {
        return baggage == null ? Collections.emptyList() : baggage;
    }

    public List<CargoRecord> getCargo() {
        return cargo == null ? Collections.emptyList() : cargo;
    }
}
