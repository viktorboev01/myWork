package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;
import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;

public class Airbnb implements AirbnbAPI{
    private final Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations){
        this.accommodations = accommodations;
    }
    @Override
    public Bookable findAccommodationById(String id){
        if (id == null || accommodations == null){
            return null;
        }
        for (Bookable acc : accommodations){
            if (id.toUpperCase().equals(acc.getId())){
                return acc;
            }
        }
        return null;
    }
    @Override
    public double estimateTotalRevenue(){
        double sum = 0;
        if (accommodations == null){
            return 0;
        }
        for (Bookable acc : accommodations){
            if (acc == null || acc.getTotalPriceOfStay() == -1){
                continue;
            }
            sum += acc.getTotalPriceOfStay();
        }
        return sum;
    }
    @Override
    public long countBookings(){
        long sum = 0;
        if (accommodations == null){
            return 0;
        }
        for (Bookable acc : accommodations) {
            if (acc == null){
                continue;
            }
            if (acc.isBooked()){
                sum++;
            }
        }
        return sum;
    }

    @Override
    public Bookable[] filterAccommodations(Criterion... criteria){
        if (accommodations == null){
            return null;
        }
        Bookable[] temp = new Bookable[accommodations.length];
        int p = 0;
        boolean suitable = true;
        for (Bookable acc : accommodations){
            if (acc == null){
                continue;
            }
            for (Criterion c : criteria){
                if (!c.check(acc)){
                    suitable = false;
                    break;
                }
            }
            if (!suitable){
                suitable = true;
            }
            else{
                temp[p] = acc;
                p++;
            }
        }
        Bookable[] result = new Bookable[p];
        for (int i = 0; i < p; i++){
            result[i] = temp[i];
        }
        return result;
    }
}