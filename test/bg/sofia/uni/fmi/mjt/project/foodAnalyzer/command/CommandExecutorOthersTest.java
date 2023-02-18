package bg.sofia.uni.fmi.mjt.foodAnalyzer.command;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.Cache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.FoodAnalyzerCache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Raffaello;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandExecutorOthersTest {
    private static Cache cache;
    private static CommandExecutor cmdExecutor;

    @BeforeAll
    public static void setUp() {
        cache = new FoodAnalyzerCache();
        cmdExecutor = new CommandExecutor(cache);
        cmdExecutor.execute(Raffaello.GET_FOOD);
    }

    @Test
    public void testGetByNameMultipleTimes() {
        for (int i = 0; i < 1000; i++) {
            cmdExecutor.execute(Raffaello.GET_FOOD);
        }
        String actual = cmdExecutor.execute(Raffaello.GET_FOOD);
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByNameZeroArgs() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.NAME));
        String expected = "No arguments was put. Example: \"get-food raffaello treat\".";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByFDCIDMultipleTimes() {
        for (int i = 0; i < 1000; i++) {
            cmdExecutor.execute(Raffaello.GET_REPORT);
        }
        String actual = cmdExecutor.execute(Raffaello.GET_REPORT);
        assertEquals(Raffaello.REPORT_TO_STRING, actual);
    }

    @Test
    public void testGetByFDCIDInvalidFDCID() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.FDCID + " notDigits"));
        String expected = "Incorrect argument. The barcode/fdcid contains only digits.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByFDCIDInvalidMoreArgs() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.FDCID + " " + Raffaello.FDCID + " " + Raffaello.FDCID));
        String expected = "Invalid count of arguments: \"get-food-report\" expects 1 arguments." +
                " Example: \"get-food-report 2041155\".";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByFDCIDZeroArgs() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.FDCID));
        String expected = "Invalid count of arguments: \"get-food-report\" expects 1 arguments." +
                " Example: \"get-food-report 2041155\".";
        assertEquals(expected, actual);
    }

}
