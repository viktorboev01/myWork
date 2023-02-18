package bg.sofia.uni.fmi.mjt.foodAnalyzer.constants;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.Command;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.CommandCreator;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodReport;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONNutrient;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONReportNutrient;

public class Raffaello {

    private Raffaello() {
    }

    public static final String IMAGE_PATH = "barcode009800146130.gif";
    public static final String SEARCH_PRODUCT = "raffaello treat";
    public static final String DESCRIPTION = "RAFFAELLO, ALMOND COCONUT TREAT";

    public static final String INGREDIENTS = "VEGETABLE OILS (PALM AND SHEANUT). DRY COCONUT, SUGAR, " +
            "ALMONDS, SKIM MILK POWDER, WHEY POWDER (MILK), WHEAT FLOUR, NATURAL AND ARTIFICIAL FLAVORS, " +
            "LECITHIN AS EMULSIFIER (SOY), SALT, SODIUM BICARBONATE AS LEAVENING AGENT.";

    public static final String DATA_TYPE = "Branded";
    public static final String FDCID = "2041155";
    public static final String GTINUPC = "009800146130";

    public static final String MAIN_INFO =
            "{productName: RAFFAELLO, ALMOND COCONUT TREAT; " +
                    "dataType: Branded; fdcid: 2041155; gtinUpc: 009800146130}";

    public static final Command GET_FOOD = CommandCreator.newCommand(Const.NAME + " " + SEARCH_PRODUCT);
    public static final Command GET_REPORT = CommandCreator.newCommand(Const.FDCID + " " + FDCID);
    public static final Command GET_BARCODE_BY_CODE =
            CommandCreator.newCommand(Const.BARCODE + " --code=" + GTINUPC);
    public static final Command GET_BARCODE_BY_IMAGE = CommandCreator.newCommand(
            Const.BARCODE + " --img=" + Raffaello.IMAGE_PATH);

    public static final JSONReportNutrient PROTEINS =
            new JSONReportNutrient(new JSONNutrient(Const.PROTEINS), 2);

    public static final JSONReportNutrient FATS =
            new JSONReportNutrient(new JSONNutrient(Const.FATS), 15);

    public static final JSONReportNutrient CARBS =
            new JSONReportNutrient(new JSONNutrient(Const.CARBS), 12);

    public static final JSONReportNutrient FIBERS =
            new JSONReportNutrient(new JSONNutrient(Const.FIBERS), 1);

    public static final JSONReportNutrient CALORIES =
            new JSONReportNutrient(new JSONNutrient(Const.CALORIES), 189.9);

    public static final double SERVING_SIZE = 30.0;

    public static final JSONReportNutrient[] NUTRIENTS = {PROTEINS, FATS, FIBERS, CARBS, CALORIES};

    public static final JSONFoodReport REPORT =
            new JSONFoodReport(FDCID, DESCRIPTION, DATA_TYPE, GTINUPC, INGREDIENTS,
                    NUTRIENTS, SERVING_SIZE);

    public static final String REPORT_TO_STRING = "{productName: RAFFAELLO, ALMOND COCONUT TREAT;" + System.lineSeparator()
            + "Ingredients: VEGETABLE OILS (PALM AND SHEANUT). DRY COCONUT, SUGAR, ALMONDS, " +
            "SKIM MILK POWDER, WHEY POWDER (MILK), WHEAT FLOUR, NATURAL AND ARTIFICIAL FLAVORS, " +
            "LECITHIN AS EMULSIFIER (SOY), SALT, SODIUM BICARBONATE AS LEAVENING AGENT.;" +
            System.lineSeparator() + "Content: protein: 2 g; total lipid (fat): 15 g; fiber, total dietary: 1 g; " +
            "carbohydrate, by difference: 12 g; energy: 189,9 kcal; serving size in grams: 30.0}";

}

