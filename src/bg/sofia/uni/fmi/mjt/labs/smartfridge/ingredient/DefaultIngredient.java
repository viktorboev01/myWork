package bg.sofia.uni.fmi.mjt.smartfridge.ingredient;

import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;

public record DefaultIngredient<E extends Storable>(E item, int quantity) implements Ingredient<E> {

    @Override
    public E item() {
        return item;
    }

    @Override
    public int quantity() {
        return quantity;
    }

    public int addQuantity(int rhs) {
        return quantity + rhs;
    }

    public int removeQuantity(int rhs) {
        return quantity - rhs;
    }

    public boolean equalsExpiration(DefaultIngredient i) {
        return (i.item().getName().equals(this.item().getName()) &&
                i.item().getExpiration().equals(this.item().getExpiration()));
    }
}