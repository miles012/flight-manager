package pl.miles.flightmanager.configuration;

import pl.miles.flightmanager.domain.WeightUnit;

import java.time.ZoneId;

public final class DefaultParameters {

    public static final WeightUnit DEFAULT_WEIGHT_UNIT = WeightUnit.KG;
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");

    private DefaultParameters() {
    }


}
