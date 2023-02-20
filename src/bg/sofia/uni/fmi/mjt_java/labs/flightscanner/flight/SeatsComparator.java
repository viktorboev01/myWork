package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class SeatsComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight f1, Flight f2 ) {
        return f2.getFreeSeatsCount() - f1.getFreeSeatsCount();
    }
}
