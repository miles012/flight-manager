package pl.miles.flightmanager.domain.entity;

import java.util.Collections;
import java.util.List;

public class CargoEntity {

    private int flightId;
    private List<CargoRecord> baggage;
    private List<CargoRecord> cargo;

    public CargoEntity(int flightId, List<CargoRecord> baggage, List<CargoRecord> cargo) {
        this.flightId = flightId;
        this.baggage = baggage;
        this.cargo = cargo;
    }

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
