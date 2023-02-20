package bg.sofia.uni.fmi.mjt.foodAnalyzer.cache;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.FoodData;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodGetter;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodReport;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FoodAnalyzerCache implements Cache {

    private Set<String> notFoundReports;
    private Set<String> notFoundGetter;

    private Map<String, FoodData> reports; // String represents fdcid
    private Map<String, Set<String>> getters; // String represents get-food searchs and fdcid
    private Map<String, String> barcodes; // String represents barcode and fdcid

    private static final Set<String> NUTRIENTS_FOR_REPORT =
            Set.of(Const.PROTEINS, Const.CARBS, Const.FATS, Const.FIBERS, Const.CALORIES);

    public FoodAnalyzerCache() {
        this.reports = new ConcurrentHashMap<>();
        this.getters = new ConcurrentHashMap<>();
        this.barcodes = new ConcurrentHashMap<>();
        this.notFoundReports = new HashSet<>();
        this.notFoundGetter = new HashSet<>();
    }

    @Override
    public void addReport(JSONFoodReport report) {
        if (reports.containsKey(report.fdcId())) {
            return;
        }
        reports.put(report.fdcId(), new FoodData(report, NUTRIENTS_FOR_REPORT));

        if (report.gtinUpc() != null) {
            barcodes.put(report.gtinUpc(), report.fdcId());
        }

        getters.put(report.description().toLowerCase(), Set.of(report.fdcId()));
    }

    @Override
    public synchronized void addEmptyReport(String fdcid) {
        notFoundReports.add(fdcid);
    }

    @Override
    public synchronized void addEmptyGetter(String fdcid) {
        notFoundGetter.add(fdcid);
    }

    @Override
    public void addGetter(JSONFoodGetter foodData, String getter) {
        if (reports.containsKey(foodData.fdcId())) {
            return;
        }
        reports.put(foodData.fdcId(), new FoodData(foodData, NUTRIENTS_FOR_REPORT));

        if (foodData.gtinUpc() != null) {
            barcodes.put(foodData.gtinUpc(), foodData.fdcId());
        }

        if (!getters.containsKey(getter)) {
            getters.put(getter, new HashSet<>());
        }

        getters.get(getter).add(foodData.fdcId());
    }

    public synchronized boolean searchReport(String fdcid) {
        return reports.containsKey(fdcid) || notFoundReports.contains(fdcid);
    }

    public synchronized boolean searchGetter(String getter) {
        return getters.containsKey(getter) || notFoundGetter.contains(getter);
    }

    public boolean searchBarcode(String barcode) {
        return barcodes.containsKey(barcode);
    }

    public List<FoodData> getQueryFoods(String getter) {
        CopyOnWriteArrayList<FoodData> output = new CopyOnWriteArrayList<>();
        if (getters.containsKey(getter)) {
            for (String fdcid : getters.get(getter)) {
                output.add(reports.get(fdcid));
            }
        }
        return output;
    }

    public FoodData getReportFood(String fdcid) {
        if (notFoundReports.contains(fdcid)) {
            return null;
        }
        return reports.get(fdcid);
    }

    public FoodData getFoodByBarcode(String gtinUpc) {
        return reports.get(barcodes.get(gtinUpc));
    }

    public Collection<FoodData> getFoods() {
        return reports.values();
    }
}

