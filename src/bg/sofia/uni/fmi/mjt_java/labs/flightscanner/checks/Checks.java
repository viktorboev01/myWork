package bg.sofia.uni.fmi.mjt.flightscanner.checks;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;

public class Checks {

    protected void checkFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("A flight isn't valid");
        }
    }

    protected static void checkString(String text) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            throw new IllegalArgumentException("A text isn't valid");
        }
    }

    protected static void checkAirport(Airport airport) {
        if (airport == null) {
            throw new IllegalArgumentException("An airport isn't valid");
        }
    }
}
