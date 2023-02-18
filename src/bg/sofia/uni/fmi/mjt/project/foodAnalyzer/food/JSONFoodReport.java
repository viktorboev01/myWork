package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

public record JSONFoodReport(String fdcId, String description, String dataType,
                             String gtinUpc, String ingredients,
                             JSONReportNutrient[] foodNutrients, double servingSize) {
    public String toString() {
        return "{" + description + ": " + dataType + "; " + fdcId + "; " + gtinUpc + "}";
    }
}
