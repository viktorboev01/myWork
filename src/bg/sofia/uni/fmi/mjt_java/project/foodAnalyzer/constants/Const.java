package bg.sofia.uni.fmi.mjt.foodAnalyzer.constants;

import java.text.DecimalFormat;

public final class Const {

    private Const() {
    }

    public static final String INVALID_NULL_ARGS_MESSAGE_FORMAT =
            "No arguments was put. Example: \"%s\".";

    public static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
            "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\".";

    public static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT_BARCODE =
            "Invalid count of arguments: \"%s\" expects %d or %d arguments. Example: \"%s\".";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    public static final String NAME = "get-food";
    public static final String FDCID = "get-food-report";
    public static final String BARCODE = "get-food-by-barcode";

    public static final String STOP_READER = "stopReader";

    public static final String HOST = "localhost";
    public static final String NOT_FOUND_MESSAGE =
            "The product with the given information was not found in the system.";
    public static final int SERVER_PORT = 7777;

    public static final String DEFAULT_EXECUTE = "The command is not implemented.";
    public static final String CARBS = "carbohydrate, by difference";
    public static final String FATS = "total lipid (fat)";
    public static final String PROTEINS = "protein";
    public static final String FIBERS = "fiber, total dietary";
    public static final String CALORIES = "energy";

    public static final String IMG = "--img";
    public static final String CODE = "--code";
}

