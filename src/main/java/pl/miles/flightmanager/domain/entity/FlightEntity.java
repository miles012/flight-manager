package pl.miles.flightmanager.domain.entity;

import lombok.Builder;

import java.time.ZonedDateTime;

public class FlightEntity {

    private int flightId;
    private int flightNumber;
    private String departureAirportIATACode;
    private String arrivalAirportIATACode;
    private ZonedDateTime departureDate;

    public FlightEntity(int flightId, int flightNumber, String departureAirportIATACode, String arrivalAirportIATACode, ZonedDateTime departureDate) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departureAirportIATACode;
        this.arrivalAirportIATACode = arrivalAirportIATACode;
        this.departureDate = departureDate;
    }

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
