package pl.miles.flightmanager.domain.dto;

import pl.miles.flightmanager.domain.entity.WeightUnit;

public class FlightInfoDto {

    private final double cargoWeight;
    private final double baggageWeight;
    private final double totalWeight;
    private final WeightUnit weightUnit;

    private FlightInfoDto(Builder builder) {
        this.cargoWeight = builder.cargoWeight;
        this.baggageWeight = builder.baggageWeight;
        this.totalWeight = builder.cargoWeight + builder.baggageWeight;
        this.weightUnit = builder.weightUnit;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public double getBaggageWeight() {
        return baggageWeight;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public static class Builder {
        private double cargoWeight;
        private double baggageWeight;
        private WeightUnit weightUnit;

        public Builder cargoWeight(double cargoWeight) {
            this.cargoWeight = cargoWeight;
            return this;
        }

        public Builder baggageWeight(double baggageWeight) {
            this.baggageWeight = baggageWeight;
            return this;
        }

        public Builder weightUnit(WeightUnit weightUnit) {
            this.weightUnit = weightUnit;
            return this;
        }

        public FlightInfoDto build() {
            return new FlightInfoDto(this);
        }
    }
}
