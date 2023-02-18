package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;
import java.time.LocalDateTime;

public interface Bookable {
    String getId();

    Location getLocation();

    boolean isBooked();

    boolean book(LocalDateTime var1, LocalDateTime var2);

    double getTotalPriceOfStay();

    double getPricePerNight();
}
