package bg.sofia.uni.fmi.mjt.foodAnalyzer.food;

import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.Cache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.cache.FoodAnalyzerCache;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Const;
import bg.sofia.uni.fmi.mjt.foodAnalyzer.constants.Raffaello;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodDataExecutorReportTest {

    private static Cache cache;
    private static FoodData actualRaffaello;

    @BeforeAll
    public static void setUp() throws URISyntaxException, IOException, InterruptedException {
        cache = new FoodAnalyzerCache();
        CommandExecutor cmdExecutor = new CommandExecutor(cache);
        cmdExecutor.execute(Raffaello.GET_REPORT);
        for (FoodData food : cache.getFoods()) {
            actualRaffaello = food;
        }
    }

    @Test
    public void testGetFoodsFromCache() {
        assertEquals(1, cache.getFoods().size());
    }

    @Test
    public void testProteins() {
        assertEquals(Const.DECIMAL_FORMAT.format(Raffaello.PROTEINS.amount()),
                actualRaffaello.getNutrients().get(Raffaello.PROTEINS.nutrient().name()));
    }

    @Test
    public void testFats() {
        assertEquals(Const.DECIMAL_FORMAT.format(Raffaello.FATS.amount()),
                actualRaffaello.getNutrients().get(Raffaello.FATS.nutrient().name()));
    }

    @Test
    public void testFibers() {
        assertEquals(Const.DECIMAL_FORMAT.format(Raffaello.FIBERS.amount()),
                actualRaffaello.getNutrients().get(Raffaello.FIBERS.nutrient().name()));
    }

    @Test
    public void testCarbs() {
        assertEquals(Const.DECIMAL_FORMAT.format(Raffaello.CARBS.amount()),
                actualRaffaello.getNutrients().get(Raffaello.CARBS.nutrient().name()));
    }

    @Test
    public void testCalories() {
        assertEquals(Const.DECIMAL_FORMAT.format(Raffaello.CALORIES.amount()),
                actualRaffaello.getNutrients().get(Raffaello.CALORIES.nutrient().name()));
    }
}
