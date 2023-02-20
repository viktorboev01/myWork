package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

public record JSONReportNutrient(JSONNutrient nutrient, double amount) {
    public String toString() {
        return "{" + nutrient.name() + ": " + amount + "}";
    }
}
