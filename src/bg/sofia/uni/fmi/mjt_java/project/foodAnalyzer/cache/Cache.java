package bg.sofia.uni.fmi.mjt.foodAnalyzer.cache;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.FoodData;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodGetter;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodReport;

import java.util.Collection;
import java.util.List;

public interface Cache {

    void addReport(JSONFoodReport foodNutrients);
    void addEmptyReport(String fdcid);
    void addEmptyGetter(String fdcid);
    void addGetter(JSONFoodGetter foodNutrients, String query);
    boolean searchReport(String fdcid);
    boolean searchGetter(String query);
    boolean searchBarcode(String barcode);
    List<FoodData> getQueryFoods(String query);
    FoodData getReportFood(String fdcid);
    FoodData getFoodByBarcode(String gtinUpc);
    Collection<FoodData> getFoods();

}
