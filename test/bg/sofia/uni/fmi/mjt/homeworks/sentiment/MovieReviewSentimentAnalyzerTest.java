package bg.sofia.uni.fmi.mjt.sentiment;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MovieReviewSentimentAnalyzerTest {

    private Reader readerStopwords;
    private Reader readerReviews;
    private Writer writer;
    private MovieReviewSentimentAnalyzer analyzer;

    @BeforeEach
    public void initEach() throws IOException {

        File reviews = File.createTempFile("demo", ".txt");
        readerReviews = new FileReader(reviews);

        File stopwords = File.createTempFile("stopwords", ".txt");
        Writer writer1 = new FileWriter(stopwords, true);
        writer1.write("and" + System.lineSeparator());
        writer1.write("for" + System.lineSeparator());
        writer1.write("your" + System.lineSeparator());
        writer1.flush();

        readerStopwords = new FileReader(stopwords);

        writer = new FileWriter(reviews, true);
        writer.write("4 FantasTic cake for your outstanding birthday !" + System.lineSeparator());
        writer.flush();

        analyzer = new MovieReviewSentimentAnalyzer(readerStopwords, readerReviews, writer);
        analyzer.appendReview("DisgUiSing cake for your outstanding birthday ?", 2);
        analyzer.appendReview("DisguisiNg cake! for your boring birthday .", 0);
    }

    @AfterEach
    public void closeEach() throws IOException {
        readerStopwords.close();
        readerReviews.close();
        writer.close();
    }

    @Test
    public void testGetReviewSentiment() {
        assertEquals(2.0, analyzer.getReviewSentiment("FantasTic boring !"));
    }

    @Test
    public void testGetReviewSentimentOutOfRange() {
        assertEquals(-1,
                analyzer.getReviewSentiment("wonderful life !"));
    }

    @Test
    public void testGetReviewSentimentInvalidData() {
        assertEquals(-1,
                analyzer.getReviewSentiment("The performances are an absolute joy ."));
    }

    @Test
    public void testGetReviewSentimentAsNameSomewhatPositive() {
        assertEquals("somewhat positive",
                analyzer.getReviewSentimentAsName("FantasTic delicious cake !"));
    }

    @Test
    public void testGetReviewSentimentAsNamePositive() {
        assertEquals("positive",
                analyzer.getReviewSentimentAsName("FantaSTic bombastic !"));
    }

    @Test
    public void testGetReviewSentimentAsNameNeutral() {
        assertEquals("neutral",
                analyzer.getReviewSentimentAsName("FantaSTic boring cake !"));
    }

    @Test
    public void testGetReviewSentimentAsNameSomewhatNegative() {
        assertEquals("somewhat negative",
                analyzer.getReviewSentimentAsName("Boring disguising fish !"));
    }

    @Test
    public void testGetReviewSentimentAsNameNegative() {
        assertEquals("negative",
                analyzer.getReviewSentimentAsName("Boring person !"));
    }

    @Test
    public void testIsStopWordTrue() {
        assertTrue(analyzer.isStopWord("and"));
    }

    @Test
    public void testIsStopWordFalse() {
        assertFalse(analyzer.isStopWord("bee"));
    }

    @Test
    public void testGetWordSentiment() {
        assertEquals(1, analyzer.getWordSentiment("DisguisiNg"));
    }

    @Test
    public void testGetWordSentimentStopword() {
        assertEquals(-1, analyzer.getWordSentiment("and"));
    }

    @Test
    public void testGetWordFrequencyStopword() {
        assertEquals(-1, analyzer.getWordFrequency("and"));
    }

    @Test
    public void testGetWordFrequencyUnknownWord() {
        assertEquals(0, analyzer.getWordFrequency("habibi"));
    }

    @Test
    public void testGetWordSentimentWithNotExistedWord() {
        assertEquals(-1, analyzer.getWordSentiment("cake!"));
    }

    @Test
    public void testGetWordFrequency() {
        assertEquals(2, analyzer.getWordFrequency("DisguisiNg"));
    }

    @Test
    public void testGetMostFrequentWords() {
        assertTrue(analyzer.getMostFrequentWords(3).containsAll(List.of("birthday", "cake", "outstanding")));
    }

    @Test
    public void testGetMostPositiveWords() {
        assertTrue(analyzer.getMostPositiveWords(2).containsAll(List.of("fantastic", "outstanding")));
    }

    @Test
    public void testGetMostNegativeWords() {
        assertTrue(analyzer.getMostNegativeWords(2).containsAll(List.of("boring", "disguising")));
    }

    @Test
    public void testAppendReviewIncorrectRating() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.appendReview(null, 5), "Error: sentiment out of range");
    }

    @Test
    void appendReviewNullReview() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.appendReview(null, 1), "Error: Null review");
    }

    @Test
    void appendReviewReviewEmptyReview() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.appendReview("", 0), "Error: Empty review");
    }

    @Test
    void appendReviewBlankReview() {
        assertThrows(IllegalArgumentException.class, () ->
            analyzer.appendReview("   ", 0), "Error: Blank review");
    }

    @Test
    public void testAppendReviewGetMostPositiveWords() {
        analyzer.appendReview("Happy wonderful end", 4);
        assertTrue(analyzer.getMostPositiveWords(3).contains("happy"));
    }

    @Test
    public void testAppendReviewGetMostNegativeWords() {
        analyzer.appendReview("Sad tragic end", 0);
        assertTrue(analyzer.getMostNegativeWords(3).contains("tragic"));
    }

    @Test
    public void testGetSentimentDictionarySize() {
        assertEquals(6, analyzer.getSentimentDictionarySize());
    }
}
