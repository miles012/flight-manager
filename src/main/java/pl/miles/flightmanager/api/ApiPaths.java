package pl.miles.flightmanager.api;

final class ApiPaths {

    private ApiPaths() {
    }

    static final String API_VERSION = "/v1";
    static final String BASE_PATH = "/api" + API_VERSION;

    static final String AIRPORT_INFO = BASE_PATH + "/airportInfo";
    static final String FLIGHT_INFO = BASE_PATH + "/flightInfo";
}