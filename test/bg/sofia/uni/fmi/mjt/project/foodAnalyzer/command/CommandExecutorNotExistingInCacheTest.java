package bg.sofia.uni.fmi.mjt.foodAnalyzer.command;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.Cache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.FoodAnalyzerCache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Raffaello;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandExecutorNotExistingInCacheTest {

    private Cache cache;
    private CommandExecutor cmdExecutor;

    @BeforeEach
    public void setUp() {
        cache = new FoodAnalyzerCache();
        cmdExecutor = new CommandExecutor(cache);
    }

    @Test
    public void testGetByName() {
        String actual = cmdExecutor.execute(Raffaello.GET_FOOD);
        assertEquals(Raffaello.MAIN_INFO, actual);
    }

    @Test
    public void testGetByNameNotFound() {
        cmdExecutor.execute(CommandCreator.newCommand(Const.NAME + " abracadabra"));
        String actual = cmdExecutor.execute(CommandCreator.newCommand(Const.NAME + " abracadabra"));
        assertEquals(Const.NOT_FOUND_MESSAGE, actual);
    }

    @Test
    public void testGetByFDCID() {
        String actual = cmdExecutor.execute(Raffaello.GET_REPORT);
        assertEquals(Raffaello.REPORT_TO_STRING, actual);
    }

    @Test
    public void testGetByFDCIDNotFound() {
        cmdExecutor.execute(CommandCreator.newCommand(Const.FDCID + " 123456"));
        String actual = cmdExecutor.execute(CommandCreator.newCommand(Const.FDCID + " 123456"));
        assertEquals(Const.NOT_FOUND_MESSAGE, actual);
    }

    @Test
    public void testGetByBarcodeNotFound() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                Const.BARCODE + " --img=" + Raffaello.IMAGE_PATH));
        assertEquals(Const.NOT_FOUND_MESSAGE, actual);
    }

    @Test
    public void testNotImplementedCommand() {
        String actual = cmdExecutor.execute(CommandCreator.newCommand(
                "unrealCommand"));
        assertEquals(Const.DEFAULT_EXECUTE, actual);
    }

    @Test
    public void testGetByNameMultipleAnswers() {
        cmdExecutor.execute(CommandCreator.newCommand(
                Const.NAME + " excellence dark chocolate lindt"));
        assertEquals(3, cache.getFoods().size());
    }
}

