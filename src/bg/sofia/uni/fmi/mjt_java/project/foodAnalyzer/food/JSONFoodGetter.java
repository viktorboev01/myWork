package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

public record JSONFoodGetter(String fdcId, String description, String dataType,
                             String gtinUpc, String ingredients, double servingSize,
                             JSONGetQueryNutrient[] foodNutrients, String servingSizeUnit) {

    public String toString() {
        return "{" + description + ": " + dataType + "; " + fdcId + "; " + gtinUpc + "}";
    }
}