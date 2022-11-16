package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;

import java.util.Collection;
import java.util.List;

public interface FlightScannerAPI {

    void add(Flight flight);

    void addAll(Collection<Flight> flights);

    List<Flight> searchFlights(Airport from, Airport to);

    List<Flight> getFlightsSortedByFreeSeats(Airport from);

    List<Flight> getFlightsSortedByDestination(Airport from);

}