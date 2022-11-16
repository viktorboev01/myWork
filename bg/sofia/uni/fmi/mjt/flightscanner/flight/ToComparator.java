package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class ToComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight f1, Flight f2) {
        return f1.getTo().Id().compareTo(f2.getTo().Id());
    }
}
