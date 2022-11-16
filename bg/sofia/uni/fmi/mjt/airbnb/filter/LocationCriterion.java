package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class LocationCriterion implements Criterion {
    private Location currentLocation;
    private double maxDistance;

    public LocationCriterion(Location currentLocation, double maxDistance) {
        this.currentLocation = currentLocation;
        this.maxDistance = maxDistance;
    }

    public boolean check(Bookable bookable) {
        if (this != null && bookable != null) {
            double x1 = this.currentLocation.getX();
            double y1 = this.currentLocation.getY();
            double x2 = bookable.getLocation().getX();
            double y2 = bookable.getLocation().getY();
            double currDistance = Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
            return !(currDistance > this.maxDistance);
        } else {
            return false;
        }
    }
}
