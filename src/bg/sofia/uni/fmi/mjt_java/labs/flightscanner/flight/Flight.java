package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import bg.sofia.uni.fmi.mjt.flightscanner.airport.Airport;
import bg.sofia.uni.fmi.mjt.flightscanner.exception.FlightCapacityExceededException;
import bg.sofia.uni.fmi.mjt.flightscanner.passenger.Passenger;

import java.util.Collection;

public interface Flight {

    Airport getFrom();

    Airport getTo();

    void addPassenger(Passenger passenger) throws FlightCapacityExceededException;

    void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException;

    Collection<Passenger> getAllPassengers();

    int getFreeSeatsCount();

}