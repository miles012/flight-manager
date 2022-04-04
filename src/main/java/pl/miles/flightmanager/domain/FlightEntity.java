package pl.miles.flightmanager.domain;

import java.time.ZonedDateTime;

public class FlightEntity {

    private int flightId;
    private int flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private ZonedDateTime departureDate;

    public int getFlightId() {
        return flightId;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureAirportIATACode() {
        return departureAirportIATACode;
    }

    public String getArrivalAirportIATACode() {
        return arrivalAirportIATACode;
    }

    public ZonedDateTime getDepartureDate() {
        return departureDate;
    }
}
