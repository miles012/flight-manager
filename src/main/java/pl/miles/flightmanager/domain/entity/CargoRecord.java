package pl.miles.flightmanager.domain.entity;

public class CargoRecord {

    private int id;
    private int weight;
    private WeightUnit weightUnit;
    private int pieces;

    public CargoRecord(int id, int weight, WeightUnit weightUnit, int pieces) {
        this.id = id;
        this.weight = weight;
        this.weightUnit = weightUnit;
        this.pieces = pieces;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public int getPieces() {
        return pieces;
    }
}
