package pl.miles.flightmanager.domain.dto;

public class AirportInfoDto {

    private final int numberOfDepartures;
    private final int numberOfArrivals;
    private final int numberOfBaggageArrivals;
    private final int numberOfBaggageDepartures;

    private AirportInfoDto (Builder builder) {
        this.numberOfDepartures = builder.numberOfDepartures;
        this.numberOfArrivals = builder.numberOfArrivals;
        this.numberOfBaggageArrivals = builder.numberOfBaggageArrivals;
        this.numberOfBaggageDepartures = builder.numberOfBaggageDepartures;
    }

    public int getNumberOfDepartures() {
        return numberOfDepartures;
    }

    public int getNumberOfArrivals() {
        return numberOfArrivals;
    }

    public int getNumberOfBaggageArrivals() {
        return numberOfBaggageArrivals;
    }

    public int getNumberOfBaggageDepartures() {
        return numberOfBaggageDepartures;
    }

    public static class Builder {

        private int numberOfDepartures;
        private int numberOfArrivals;
        private int numberOfBaggageArrivals;
        private int numberOfBaggageDepartures;

        public Builder numberOfDepartures(int numberOfDepartures) {
            this.numberOfDepartures = numberOfDepartures;
            return this;
        }

        public Builder numberOfArrivals(int numberOfArrivals) {
            this.numberOfArrivals = numberOfArrivals;
            return this;
        }

        public Builder numberOfBaggageArrivals(int numberOfBaggageArrivals) {
            this.numberOfBaggageArrivals = numberOfBaggageArrivals;
            return this;
        }

        public Builder numberOfBaggageDepartures(int numberOfBaggageDepartures) {
            this.numberOfBaggageDepartures = numberOfBaggageDepartures;
            return this;
        }

        public AirportInfoDto build() {
            return new AirportInfoDto(this);
        }
    }
}
