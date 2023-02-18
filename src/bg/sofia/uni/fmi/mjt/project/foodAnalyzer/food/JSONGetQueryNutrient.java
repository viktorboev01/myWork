package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

public record JSONGetQueryNutrient(String nutrientName, double value, String unitName) {

    public String toString() {
        return "{" + nutrientName + ": " + value + " " + unitName + "}";
    }
}
