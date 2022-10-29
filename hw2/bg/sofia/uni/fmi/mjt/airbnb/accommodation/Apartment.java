package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Apartment extends ImplementBookable implements Bookable {
    private static final String prefix = "APA-";
    public static int counter = 0;

    public Apartment(Location location, double pricePerNight){
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
