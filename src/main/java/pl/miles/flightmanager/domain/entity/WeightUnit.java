package pl.miles.flightmanager.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum WeightUnit {

    KG, LB;

    private static final double LB_TO_KG = 0.45359237;

    public static double fromKgToLb(double kg) {
        return kg / LB_TO_KG;
    }

    public static double fromLbToKg(double lb) {
        return lb * LB_TO_KG;
    }

    @JsonCreator
    public static WeightUnit fromString(String weightUnit) {
        return weightUnit == null ? null : WeightUnit.valueOf(weightUnit.toUpperCase());
    }

}
