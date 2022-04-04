package pl.miles.flightmanager.domain;

public class CargoRecord {

    private int id;
    private int weight;
    private WeightUnit weightUnit;
    private int pieces;

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
