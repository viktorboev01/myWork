package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Hotel extends ImplementBookable implements Bookable {
    private static final String prefix = "HOT-";
    private static int counter = 0;

    public Hotel(Location location, double pricePerNight){
        if (pricePerNight < 0 || location == null){
            return;
        }
        this.ID = prefix + Integer.toString(counter);
        this.location = location;
        this.checkIn = null;
        this.checkOut = null;
        this.pricePerNight = pricePerNight;
        counter++;
    }
}

