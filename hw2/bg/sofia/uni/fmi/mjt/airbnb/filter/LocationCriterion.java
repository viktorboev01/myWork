package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;

public class LocationCriterion implements Criterion {

    private Location currentLocation;
    private double maxDistance;

    public LocationCriterion(Location currentLocation, double maxDistance) {
        this.currentLocation = currentLocation;
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(Bookable bookable){
        if (this == null || bookable == null){
            return false;
        }
        double x1 = currentLocation.getX();
        double y1 = currentLocation.getY();

        double x2 = bookable.getLocation().getX();
        double y2 = bookable.getLocation().getY();

        double currDistance = Math.sqrt(Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));
        if (currDistance > maxDistance){
            return false;
        }
        return true;
    }
}
