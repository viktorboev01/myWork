package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Villa extends ImplementBookable implements Bookable {

    private static final String prefix = "VIL-";
    public static int counter = 0;

    public Villa(Location location, double pricePerNight){
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

