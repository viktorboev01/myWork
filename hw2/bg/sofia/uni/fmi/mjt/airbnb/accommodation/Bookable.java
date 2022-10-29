package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import java.time.LocalDateTime;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public interface Bookable {

    String getId();

    Location getLocation();

    boolean isBooked();

    boolean book(LocalDateTime checkIn, LocalDateTime checkOut);

    double getTotalPriceOfStay();

    double getPricePerNight();

}