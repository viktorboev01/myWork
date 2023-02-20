package bg.sofia.uni.fmi.mjt.flightscanner;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.checks.Checks;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.Flight;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.SeatsComparator;
import bg.sofia.uni.fmi.mjt.flightscanner.flight.ToComparator;

import java.util.*;

public class FlightScanner extends Checks implements FlightScannerAPI {

    private List<Flight> flights;

    private List<Flight> getFlightsStartsFrom(Airport from) {
        List<Flight> l = new LinkedList<>();

        for (Flight f : flights) {
            if (f.getFrom().equals(from)) {
                l.add(f);
            }
        }

        return l;
    }

    private List<Flight> fillListByMap(Map<Flight, Flight> map, Airport from, Flight end) {

        List<Flight> result = new LinkedList<>();
        Flight temp = end;

        while (temp != null) {
            result.add(temp);
            temp = map.get(temp);
        }
        return result;
    }
    private List<Flight> bfsFlights(Airport from, Airport to) {

        Map<Flight, Flight> childParent = new HashMap<>();
        Set<Airport> visited = new HashSet<>();
        Queue<Flight> q1 = new LinkedList<>();
        q1.addAll(getFlightsStartsFrom(from));
        q1.add(null);
        boolean isLastNull = false;

        while (!q1.isEmpty()) {

            Flight flightFrom = q1.peek();
            q1.remove();

            if (flightFrom == null) {
                if (isLastNull) {
                    break;
                }
                q1.add(null);
                isLastNull = true;

            }
            else {
                visited.add(flightFrom.getFrom());
                isLastNull = false;

                // One leg
                if (flightFrom.getTo().equals(to)) {
                    return List.of(flightFrom);
                }
                for (Flight flightTo : getFlightsStartsFrom(flightFrom.getTo())) {

                    if (!visited.contains(flightTo.getFrom())) {
                        q1.add(flightTo);
                        childParent.put(flightTo, flightFrom);
                    }

                    // More than one leg
                    if (flightTo.getTo().equals(to)) {
                        List<Flight> l = fillListByMap(childParent, from, flightTo);
                        Collections.reverse(l);
                        return l;
                    }
                }
            }
        }
        return List.of();
    }

    public FlightScanner() {
        flights = new LinkedList<>();
    }
    public void add(Flight flight) {
        checkFlight(flight);
        if (!flights.contains(flight)) {
            flights.add(flight);
        }
    }

    public void addAll(Collection<Flight> flights) {
        for (Flight f : flights) {
            this.add(f);
        }
    }

    public List<Flight> searchFlights(Airport from, Airport to) {

        checkAirport(from);
        checkAirport(to);

        if (from.equals(to)) {
            throw new IllegalArgumentException("From:" + from.Id() + " and To:" + to.Id() + " are equal");
        }

        return bfsFlights(from, to);
    }


    public List<Flight> getFlightsSortedByFreeSeats(Airport from) {

        List<Flight> l = getFlightsStartsFrom(from);
        l.sort(new SeatsComparator());
        return l;
    }

    public List<Flight> getFlightsSortedByDestination(Airport from) {

        List<Flight> l = getFlightsStartsFrom(from);
        l.sort(new ToComparator());
        return l;
    }
}


