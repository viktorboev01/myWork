package bg.sofia.uni.fmi.mjt.foodAnalyzer.command;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.Cache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Raffaello;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.FoodData;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodGetter;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.food.JSONFoodReport;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.reader.BarcodeReader;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//reference:
//https://github.com/fmi/java-course/blob/master/11-network-ii/snippets/todo-list-app/src/bg/sofia/uni/fmi/mjt/todo/command/CommandExecutor.java
public class CommandExecutor {
    private static final String SCHEME = "https";
    private static final String AUTHORITY = "api.nal.usda.gov";
    private static final String WEB_PATH_FOODS = "/fdc/v1/foods";
    private static final String WEB_PATH_FOOD = "/fdc/v1/food";
    private static final String API_KEY = "8oqZpdYF5y8QF38vhPqBnkbY0Wgd7Ek9gE7F72MB";

    private Cache cache;

    public CommandExecutor(Cache cache) {
        this.cache = cache;
    }

    public String execute(Command cmd) {
        try {
            return switch (cmd.command()) {
                case Const.NAME -> getByName(cmd.arguments());
                case Const.FDCID -> getByFDCID(cmd.arguments());
                case Const.BARCODE -> getByBarcode(cmd.arguments());
                default -> Const.DEFAULT_EXECUTE;
            };
        } catch (IllegalArgumentException | IOException | URISyntaxException | InterruptedException e) {
            return e.getMessage();
        }
    }

    public String getByName(String[] args) throws URISyntaxException, IOException, InterruptedException {

        if (args.length == 0) {
            throw new IllegalArgumentException(String.format(Const.INVALID_NULL_ARGS_MESSAGE_FORMAT,
                    Const.NAME + " " + Raffaello.SEARCH_PRODUCT));
        }

        String foodName = String.join(" ", args).replace("\n", "")
                .replace("\r", "").toLowerCase();

        if (cache.searchGetter(foodName)) {
            if (cache.getQueryFoods(foodName).isEmpty()) {
                return Const.NOT_FOUND_MESSAGE;
            }
            return showFoods(cache.getQueryFoods(foodName));
        }

        HttpClient client =
                HttpClient.newBuilder().build();

        URI uri = new URI(SCHEME, AUTHORITY, WEB_PATH_FOODS + "/search",
                "query=" + foodName + "&requireAllWords=true&api_key=" + API_KEY,
                null);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        String answer = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        Gson gson = new Gson();
        String splitInFrontPartFoods = answer.split("\"foods\":")[1];
        String partFoods = splitInFrontPartFoods.split(",\"aggregations\":")[0];

        List<JSONFoodGetter> output =
                Arrays.stream(gson.fromJson(partFoods, JSONFoodGetter[].class)).toList();

        if (putFoods(output, foodName)) {
            return showFoods(cache.getQueryFoods(foodName));
        }

        return Const.NOT_FOUND_MESSAGE;
    }

    public String getByFDCID(String[] args) throws URISyntaxException, IOException, InterruptedException {

        if (args.length != 1) {
            throw new IllegalArgumentException(String.format(Const.INVALID_ARGS_COUNT_MESSAGE_FORMAT, Const.FDCID, 1,
                    Const.FDCID + " " + Raffaello.FDCID));
        }

        String fdcid = args[0].replace("\n", "").replace("\r", "");
        checkDigitString(fdcid);

        if (cache.searchReport(fdcid)) {
            if (cache.getReportFood(fdcid) == null) {
                return Const.NOT_FOUND_MESSAGE;
            }
            return cache.getReportFood(fdcid).showFoodReport();
        }

        HttpClient client =
                HttpClient.newBuilder().build();

        URI uri = new URI(SCHEME, AUTHORITY, WEB_PATH_FOOD + "/" + fdcid, "api_key=" + API_KEY, null);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();

        String answer = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        if (answer.isEmpty()) {
            cache.addEmptyReport(fdcid);
            return Const.NOT_FOUND_MESSAGE;
        }

        Gson gson = new Gson();
        cache.addReport(gson.fromJson(answer, JSONFoodReport.class));

        return cache.getReportFood(fdcid).showFoodReport();
    }

    public String getByBarcode(String[] args) {

        if (args.length > 2) {
            throw new IllegalArgumentException(String.format(String.format(
                    Const.INVALID_ARGS_COUNT_MESSAGE_FORMAT_BARCODE, Const.BARCODE, 1, 2,
                    Const.BARCODE + " " + Const.CODE + "=" + Raffaello.GTINUPC)));
        }

        if (args.length == 1) {
            return getByBarcodeOneArg(args);
        } else if (args.length == 2) {
            return getByBarcodeTwoArgs(args);
        } else {
            throw new IllegalArgumentException("No arguments was given.");
        }
    }

    private boolean putFoods(List<JSONFoodGetter> answer, String foodName) {
        if (answer.isEmpty()) {
            cache.addEmptyGetter(foodName);
            return false;
        }

        for (JSONFoodGetter food : answer) {
            cache.addGetter(food, foodName);
        }

        return true;
    }

    private String showFoods(List<FoodData> foods) {
        StringBuffer str = new StringBuffer();
        for (FoodData food : foods) {
            str.append(food.showFoodMainInfo() + '\n');
        }
        return str.substring(0, str.length() - 1);
    }

    private String getBarcodeByCode(String code) {

        checkDigitString(code);

        if (!cache.searchBarcode(code)) {
            return Const.NOT_FOUND_MESSAGE;
        }

        return cache.getFoodByBarcode(code).showFoodMainInfo();
    }

    private String getByBarcodeOneArg(String args[]) {
        String[] command = args[0].replace("\n", "")
                .replace("\r", "").split("=");
        return getByBarcodeOneArgFromStrings(command);
    }

    private String getByBarcodeTwoArgs(String args[]) {
        String[] firstCommand = args[0].split("=");
        String[] secondCommand = args[1].split("=");

        if (firstCommand[0].equals(Const.CODE) && secondCommand[0].equals(Const.IMG)) {
            return getBarcodeByCode(firstCommand[1]);

        } else if (firstCommand[0].equals(Const.IMG) && secondCommand[0].equals(Const.CODE)) {
            return getBarcodeByCode(secondCommand[1]);

        } else if ((firstCommand[0].equals(Const.IMG) && secondCommand[0].equals(Const.IMG))
                || (firstCommand[0].equals(Const.CODE) && secondCommand[0].equals(Const.CODE))) {
            return getByBarcodeOneArgFromStrings(firstCommand);

        } else {
            throw new IllegalArgumentException("At least one of the commands: <"
                    + firstCommand[0] + "> or <" + secondCommand[0] + "> is incorrect." +
                    " Correct ones are <" + Const.CODE + "> and <" + Const.IMG + ">.");
        }
    }

    private void checkDigitString(String text) {
        if (!text.matches("^[0-9]+$")) {
            throw new IllegalArgumentException("Incorrect argument. " +
                    "The barcode/fdcid contains only digits.");
        }
    }

    private String getByBarcodeOneArgFromStrings(String[] command) {
        if (command[0].equals(Const.IMG)) {
            try {
                BarcodeReader barcodeReader = new BarcodeReader();
                String code = barcodeReader.read(command[1]);
                return getBarcodeByCode(code);
            } catch (Exception e) {
                throw new RuntimeException("The given image as argument: <"
                        + command[1] + "> wasn't found.");
            }

        } else if (command[0].equals(Const.CODE)) {
            return getBarcodeByCode(command[1]);
        } else {
            throw new IllegalArgumentException("Incorrect first part of the argument: <" +
                    command[0] + "> can be <" + Const.CODE + "> or <" + Const.IMG + ">.");
        }
    }
}

