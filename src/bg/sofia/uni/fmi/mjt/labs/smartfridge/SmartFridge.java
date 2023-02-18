package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.checks.SmartFridgeChecks;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.DefaultIngredient;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.IngredientComparator;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

import java.util.*;

public class SmartFridge<E> extends SmartFridgeChecks implements SmartFridgeAPI {

    private int currentCapacity;
    private Map<String, Set<DefaultIngredient>> items;

    private void removeNElementsFromSet(int n, Set<DefaultIngredient> set) {
        while (n != 0) {
            set.remove(set.iterator().next());
            n--;
        }
    }

    public SmartFridge(int totalCapacity) {
        this.currentCapacity = totalCapacity;
        this.items = new HashMap<>();
    }

    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {

        checkItem(item);
        checkCapacity(quantity, currentCapacity);
        checkQuantity(quantity);

        currentCapacity -= quantity;
        String name = item.getName();
        DefaultIngredient<E> ingredient = new DefaultIngredient<>(item, quantity);

        if (!items.containsKey(name)) {
            Set<DefaultIngredient> set = new TreeSet<>(new IngredientComparator());
            set.add(ingredient);
            items.put(name, set);

        } else if (!items.get(name).contains(ingredient)) {
            items.get(name).add(ingredient);

        } else {
            for (DefaultIngredient<E> i : items.get(name)) {
                if (i.equalsExpiration(ingredient)) {
                    DefaultIngredient<E> temp = new DefaultIngredient<E>(i.item(), i.addQuantity(quantity));
                    items.get(name).remove(temp);
                    items.get(name).add(temp);
                    break;
                }
            }
        }
    }

    public List<? extends Storable> retrieve(String itemName) {

        checkString(itemName);

        List<Storable> res = new ArrayList<>();
        if (items.get(itemName) == null) {
            return res;
        }

        for (DefaultIngredient i : items.get(itemName)) {
            for (int j = 0; j < i.quantity(); j++) {
                res.add(i.item());
                currentCapacity += 1;
            }
        }
        items.remove(itemName);
        return res;
    }

    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {

        checkString(itemName);
        checkMissing(itemName, items);
        checkQuantity(quantity);

        int linesToRemove = 0;
        List<Storable> res = new ArrayList<>();

        for (DefaultIngredient i : items.get(itemName)) {
            int size = i.quantity();
            for (int j = 0; j < size; j++) {

                if (quantity == 0) {
                    removeNElementsFromSet(linesToRemove, items.get(itemName));
                    items.get(itemName).remove(items.get(itemName).iterator().next());
                    items.get(itemName).add(i);
                    return res;
                }

                res.add(i.item());
                i = new DefaultIngredient(i.item(), i.removeQuantity(1));
                quantity -= 1;
            }
            linesToRemove++;
        }

        if (quantity > 0) {
            throw new InsufficientQuantityException("Insufficient quantity of " + itemName);
        }
        items.remove(itemName);
        return res;
    }

    public int getQuantityOfItem(String itemName) {

        checkString(itemName);

        int res = 0;
        if (items.get(itemName) == null) {
            return 0;
        }
        for (DefaultIngredient i : items.get(itemName)) {
            res += i.quantity();
        }
        return res;
    }

    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {

        checkRecipe(recipe);

        List<Ingredient<? extends Storable>> list = new LinkedList<>();
        List<Storable> listSearchExpired = List.of();
        int counterExpired = 0;

        for (Ingredient ingredient : recipe.getIngredients()) {
            try {
                listSearchExpired.addAll(this.retrieve(ingredient.item().getName(), ingredient.quantity()));
                for (Storable item : listSearchExpired) {
                    if (item.isExpired()) {
                        counterExpired += 1;
                    }
                    else {
                        if (listSearchExpired.size() - counterExpired < ingredient.quantity()) {
                            list.add(ingredient);
                        }
                        counterExpired = 0;
                        break;
                    }
                }
            } catch (InsufficientQuantityException e) {
                list.add(ingredient);
            }
        }
        return list.iterator();
    }

    public List<? extends Storable> removeExpired() {

        List<Storable> res = new ArrayList<>();
        int counter = 0;
        for (Iterator<Set<DefaultIngredient>> itr = items.values().iterator(); itr.hasNext(); ) {
            for (DefaultIngredient i : itr.next()) {
                if (!i.item().isExpired()) {
                    try {
                        this.retrieve(i.item().getName(), counter);
                        currentCapacity += counter;
                        counter = 0;
                    } catch (InsufficientQuantityException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                counter += i.quantity();
                for (int j = 0; j < i.quantity(); j++) {
                    res.add(i.item());
                }
            }
        }
        return List.copyOf(res);
    }
}
