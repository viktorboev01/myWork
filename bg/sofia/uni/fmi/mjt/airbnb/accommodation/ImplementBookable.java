package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class ImplementBookable implements Bookable {
    protected LocalDateTime checkIn;
    protected LocalDateTime checkOut;
    protected double pricePerNight;
    protected String ID;
    protected Location location;

    public ImplementBookable() {
    }

    public Location getLocation() {
        return this.location;
    }

    private boolean ValidationChecks(LocalDateTime checkIn, LocalDateTime checkOut) {
        return checkIn != null && checkOut != null && !checkIn.isAfter(checkOut) && !checkOut.equals(checkIn) && !checkIn.isBefore(LocalDateTime.now());
    }

    public boolean isBooked() {
        return this.ValidationChecks(this.checkIn, this.checkOut);
    }

    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (this.isBooked()) {
            return false;
        } else if (!this.ValidationChecks(checkIn, checkOut)) {
            return false;
        } else {
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            return true;
        }
    }

    public double getTotalPriceOfStay() {
        if (!this.isBooked()) {
            return -1.0;
        } else {
            Duration duration = Duration.between(this.checkIn, this.checkOut);
            return this.getPricePerNight() * (double)duration.toDays();
        }
    }

    public double getPricePerNight() {
        return this.pricePerNight;
    }

    public String getId() {
        return this.ID;
    }
}
