package bg.sofia.uni.fmi.mjt.smartfridge.ingredient;

import java.util.Comparator;

public class IngredientComparator implements Comparator<DefaultIngredient> {

    @Override
    public int compare(DefaultIngredient f1, DefaultIngredient f2 ) {
        if (f1.item().getName().equals(f2.item().getName()) &&
                f1.item().getExpiration().equals(f2.item().getExpiration())) {
            return 0;
        }
        else if (f1.item().getName().equals(f2.item().getName()) &&
                f1.item().getExpiration().isBefore(f2.item().getExpiration())) {
            return -1;
        }
        else if (f1.item().getName().equals(f2.item().getName()) &&
                f1.item().getExpiration().isAfter(f2.item().getExpiration())) {
            return 1;
        }
        else {
            return f1.item().getName().compareTo(f2.item().getName());
        }
    }
}
