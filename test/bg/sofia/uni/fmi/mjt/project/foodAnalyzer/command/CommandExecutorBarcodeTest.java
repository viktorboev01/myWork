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
public class CommandExecutorBarcodeTest {

    private static Cache cache;
    private static CommandExecutor cmdExecutor;

    @BeforeAll
    public static void setUp() {
        cache = new FoodAnalyzerCache();
        cmdExecutor = new CommandExecutor(cache);
        cmdExecutor.execute(CommandCreator.newCommand(Const.NAME + " " + Raffaello.SEARCH_PRODUCT));
    }

    @Test
    public void testGetByBarcodeInvalidCode() {

        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                        Const.BARCODE + " --code=notDigits"));
        String expected = "Incorrect argument. The barcode/fdcid contains only digits.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByBarcodeCode() {
        String actual = cmdExecutor.execute(Raffaello.GET_BARCODE_BY_CODE);
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeImage() {
        String actual = cmdExecutor.execute(Raffaello.GET_BARCODE_BY_IMAGE);
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeInvalidImage() {
        assertThrows(RuntimeException.class, () -> cmdExecutor.execute(CommandCreator.newCommand(
                        Const.BARCODE + " --img=non-existing/path")),
                "The given image as argument wasn't found");
    }

    @Test
    public void testGetByBarcodeInvalidCommand() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --image=" + Raffaello.IMAGE_PATH));
        String expected = "Incorrect first part of the argument: <--image> can be <--code> or <--img>.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByBarcodeInvalidFirstCommand() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --image=" + Raffaello.IMAGE_PATH + " --code=" + Raffaello.GTINUPC));
        String expected = "At least one of the commands: <--image> or <--code> is incorrect." +
                " Correct ones are <--code> and <--img>.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByBarcodeInvalidSecondCommand() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --img=" + Raffaello.IMAGE_PATH + " --barcode=" + Raffaello.GTINUPC));
        String expected = "At least one of the commands: <--img> or <--barcode> is incorrect." +
                " Correct ones are <--code> and <--img>.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByBarcodeTwoCode() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --code=" + Raffaello.GTINUPC + " --code=" + Raffaello.GTINUPC));
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeTwoImage() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --img=" + Raffaello.IMAGE_PATH + " --img=" + Raffaello.IMAGE_PATH));
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeCodeImage() throws URISyntaxException, IOException, InterruptedException {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --img=non-existing/path" + " --code=" + Raffaello.GTINUPC));
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeImageCode() throws URISyntaxException, IOException, InterruptedException {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --code=" + Raffaello.GTINUPC + " --img=non-existing/path"));
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByBarcodeZeroArgs() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                        Const.BARCODE));
        String expected = "No arguments was given.";
        assertEquals(expected, actual);
    }

    @Test
    public void testGetByBarcodeInvalidMoreArgs() {
        String actual =  cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --code=" + Raffaello.GTINUPC
                                + " --img=" + Raffaello.IMAGE_PATH + " --mode=auto"));
        String expected = "Invalid count of arguments: \"get-food-by-barcode\" expects 1 or 2 arguments." +
                " Example: \"get-food-by-barcode --code=009800146130\".";
        assertEquals(expected, actual);
    }
}
