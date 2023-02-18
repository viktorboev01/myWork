package bg.sofia.uni.fmi.mjt.smartfridge.checks;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.DefaultIngredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.*;

public class SmartFridgeChecks {
    protected void checkString(String text) {
        if (text == null || text.isBlank() || text.isEmpty()) {
            throw new IllegalArgumentException("Item in SmartFridge is null");
        }
    }

    protected void checkMissing(String text, Map<String, Set<DefaultIngredient>> items )
            throws InsufficientQuantityException {
        if (items == null || items.get(text) == null) {
            throw new InsufficientQuantityException("Insufficient quantity of " + text);
        }
    }

    protected void checkItem(Storable item) {
        if (item == null) {
            throw new IllegalArgumentException("Item in SmartFridge is null");
        }
        checkString(item.getName());
    }

    protected void checkCapacity(int quantity, int currentCapacity) throws FridgeCapacityExceededException {
        if (currentCapacity - quantity < 0) {
            throw new FridgeCapacityExceededException("Not enough space for " + quantity + " items SmartFridge");
        }
    }

    protected void checkQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Negative quantity number" );
        }
    }

    protected void checkRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("An recipe is null");
        }
    }
}
