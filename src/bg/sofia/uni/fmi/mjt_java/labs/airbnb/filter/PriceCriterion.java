package bg.sofia.uni.fmi.mjt.airbnb.filter;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;

public class PriceCriterion implements Criterion {
    private final double minPrice;
    private final double maxPrice;

    public PriceCriterion(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public boolean check(Bookable bookable) {
        return !(bookable.getPricePerNight() < this.minPrice) && !(bookable.getPricePerNight() > this.maxPrice);
    }
}
