package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;

public interface AirbnbAPI {
    Bookable findAccommodationById(String var1);

    double estimateTotalRevenue();

    long countBookings();

    Bookable[] filterAccommodations(Criterion... var1);
}
