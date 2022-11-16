package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.InvalidFlightException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Gender;
import bg.sofia.uni.fmi.mjt.flightscanner.checks.Checks;
import java.util.*;

import static java.util.Collections.unmodifiableCollection;

public class RegularFlight extends Checks implements Flight {

    private Airport from;
    private Airport to;
    private String flightId;
    private List<Passenger> passengers;
    private int totalCapacity;

    public RegularFlight() { }

    private RegularFlight(String flightId, Airport from, Airport to, int totalCapacity) {
        this.flightId = flightId;
        this.from = from;
        this.to = to;
        this.totalCapacity = totalCapacity;
        this.passengers = new LinkedList<>();
    }

    public static RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity) {
        checkString(flightId);
        checkAirport(from);
        checkAirport(to);

        if (from.equals(to)) {
            throw new InvalidFlightException("From:" + from.Id() + " and To:" + to.Id() + " are equal");
        }

        if (totalCapacity < 0) {
            throw new IllegalArgumentException("Negative totalCapacity");
        }

        return new RegularFlight(flightId, from, to, totalCapacity);
    }

    @Override
    public Airport getFrom() {
        return from;
    }

    @Override
    public Airport getTo() {
        return to;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if (passengers.size() == totalCapacity) {
            throw new FlightCapacityExceededException("In method addPassenger the flight is full");
        }
        passengers.add(passenger);
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        if (this.passengers.size() + passengers.size() > totalCapacity) {
            throw new FlightCapacityExceededException("In method addPassengers the flight is full");
        }
        for (Passenger p : passengers) {
            this.passengers.add(p);
        }
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return unmodifiableCollection(passengers);
    }

    @Override
    public int getFreeSeatsCount() {
        return totalCapacity - passengers.size();
    }

}