package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class ImplementBookable implements Bookable {
    protected LocalDateTime checkIn, checkOut;
    protected double pricePerNight;
    protected String ID;
    protected Location location;
    @Override
    public Location getLocation(){
        return this.location;
    }

    private boolean ValidationChecks(LocalDateTime checkIn, LocalDateTime checkOut){
        if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)
                || checkOut.equals(checkIn) || checkIn.isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isBooked(){
        return ValidationChecks(checkIn, checkOut);
    }

    @Override
    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut){
        if (isBooked()){
            return false;
        }
        if(!ValidationChecks(checkIn, checkOut)){
            return false;
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        return true;
    }
    @Override
    public double getTotalPriceOfStay(){
        if (!isBooked()){
            return 0;
        }
        Duration duration = Duration.between(checkIn, checkOut);
        return getPricePerNight() * (double) duration.toDays();
    }
    @Override
    public double getPricePerNight(){
        return pricePerNight;
    }
    @Override
    public String getId(){
        return ID;
    }

}
