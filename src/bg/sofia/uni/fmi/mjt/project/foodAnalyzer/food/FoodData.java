package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FoodData {
    private String fdcId;
    private String description;
    private String dataType;
    private String gtinUpc;
    private String ingredients;
    private double servingSize;
    private Map<String, String> nutrients;

    public boolean equals(FoodData rhs) {
        return fdcId.equals(rhs.fdcId);
    }

    public int hashCode() {
        return Integer.parseInt(fdcId);
    }

    public FoodData(JSONFoodGetter jsonData, Set<String> reportNutrients) {

        this.fdcId = jsonData.fdcId();
        this.dataType = jsonData.dataType();
        this.description = jsonData.description();

        this.nutrients = new HashMap<>();
        this.servingSize = jsonData.servingSize();
        this.ingredients = jsonData.ingredients();
        this.gtinUpc = jsonData.gtinUpc();

        for (JSONGetQueryNutrient nutrient : jsonData.foodNutrients()) {
            String name = nutrient.nutrientName().toLowerCase();
            if (reportNutrients.contains(name)) {
                this.nutrients.put(name, Const.DECIMAL_FORMAT.format((nutrient.value() / 100) * servingSize));
            }
        }
    }

    public FoodData(JSONFoodReport jsonData, Set<String> reportNutrients) {

        this.fdcId = jsonData.fdcId();
        this.dataType = jsonData.dataType();
        this.description = jsonData.description();

        this.nutrients = new HashMap<>();
        this.servingSize = jsonData.servingSize();
        this.ingredients = jsonData.ingredients();
        this.gtinUpc = jsonData.gtinUpc();

        for (JSONReportNutrient nutrient : jsonData.foodNutrients()) {
            String name = nutrient.nutrient().name().toLowerCase();
            if (reportNutrients.contains(name)) {
                this.nutrients.put(name, Const.DECIMAL_FORMAT.format(
                        (nutrient.amount() / 100) * servingSize));
            }
        }
    }

    public FoodData(String fdcId, String description, String dataType,
                    String gtinUpc, String ingredients, double servingSize, Map<String, String> nutrients) {

        this.fdcId = fdcId;
        this.dataType = description;
        this.description = dataType;

        this.nutrients = nutrients;
        this.servingSize = servingSize;
        this.ingredients = ingredients;
        this.gtinUpc = gtinUpc;
    }

    public synchronized String showFoodMainInfo() {
        return "{productName: " + description + "; dataType: " + dataType + "; fdcid: " + fdcId + "; gtinUpc: " + gtinUpc + "}";
    }

    public String showFoodReport() {

        StringBuffer output = new StringBuffer();
        output.append("{productName: " + description
                + ";" + System.lineSeparator() + "Ingredients: " + ingredients
                + ";" + System.lineSeparator() + "Content: ");

        for (String nutrient : nutrients.keySet()) {
            if (nutrient.equals("energy")) {
                output.append(nutrient + ": " + nutrients.get(nutrient) + " kcal; ");
            } else {
                output.append(nutrient + ": " + nutrients.get(nutrient) + " g; ");
            }
        }

        output.append("serving size in grams: " + servingSize + '}');
        return output.toString();
    }

    public Map<String, String> getNutrients() {
        return this.nutrients;
    }
}
