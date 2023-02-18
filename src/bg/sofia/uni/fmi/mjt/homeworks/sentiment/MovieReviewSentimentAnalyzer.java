package bg.sofia.uni.fmi.mjt.sentiment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {

    private static final Pattern PATTERN_WORDS =
            Pattern.compile("\\b[A-Za-z0-9']{2,}\\b", Pattern.CASE_INSENSITIVE);

    private static final int SENTIMENT_MIN = 0;

    private static final int SENTIMENT_MAX = 4;

    private final Set<String> stopwords;

    private HashMap<String, Double> wordAndRatings;

    private HashMap<String, Integer> wordAndFrequencies;

    private HashMap<String, Integer> frequenciesForRating;

    private TreeMap<Double, Set<String>> ratingsAndWord;

    private TreeMap<Double, Set<String>> frequenciesAndWord;

    private Reader stopwordsIn;

    private Reader reviewsIn;

    private Writer reviewsOut;

    private List<String> getMostNthElementsFromSet(TreeMap<Double, Set<String>> map, int n, boolean getBiggest) {

        List<String> list = new ArrayList<>();
        Set<Double> keySet;

        if (getBiggest) {
            keySet = map.descendingKeySet();
        } else {
            keySet = map.keySet();
        }

        for (Double key : keySet) {

            for (String word : map.get(key)) {

                list.add(word);
                if (--n == 0) {
                    return list;
                }
            }
        }

        return list;
    }

    private List<String> createListOfMatches(String line) {
        List<String> allMatches = new ArrayList<>();
        Matcher matcher = PATTERN_WORDS.matcher(line);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches;
    }

    private void putInRating(String str, double rating) {
        if (ratingsAndWord.containsKey(rating)) {
            ratingsAndWord.get(rating).add(str);
        } else {
            Set<String> set = new HashSet<>();
            set.add(str);
            ratingsAndWord.put(rating, set);
        }
    }

    private void putInFrequency(String str, Double frequency) {
        if (frequenciesAndWord.containsKey(frequency)) {
            frequenciesAndWord.get(frequency).add(str);
        } else {
            Set<String> set = new HashSet<>();
            set.add(str);
            frequenciesAndWord.put(frequency, set);
        }
    }

    private void fillSortedSetsWithNewWord(String str, double sentiment) {
        wordAndRatings.put(str, sentiment);
        wordAndFrequencies.put(str, 1);
        double rating = wordAndRatings.get(str);

        putInRating(str, rating);
        putInFrequency(str, 1.0);
    }

    private void fillSortedSetsWithExistingWordRating(String str, double rating) {

        double frequency = frequenciesForRating.get(str);
        double oldRating = wordAndRatings.get(str);
        double newRating = (oldRating * frequency + rating) / (frequency + 1);
        wordAndRatings.put(str, newRating);

        if (ratingsAndWord.get(oldRating).size() == 1) {
            ratingsAndWord.remove(oldRating);
        } else {
            ratingsAndWord.get(oldRating).remove(str);
        }

        putInRating(str, newRating);
    }

    private void fillSortedSetsWithExistingWordFrequency(String str) {

        double oldFrequency = wordAndFrequencies.get(str);
        double newFrequency = oldFrequency + 1;
        wordAndFrequencies.put(str, (int) newFrequency);

        if (frequenciesAndWord.get(oldFrequency).size() == 1) {
            frequenciesAndWord.remove(oldFrequency);
        } else {
            frequenciesAndWord.get(oldFrequency).remove(str);
        }

        putInFrequency(str, newFrequency);
    }

    private void fillStoppers() {

        try (BufferedReader bufferedReader = new BufferedReader(stopwordsIn)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stopwords.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processWordsFromLine(String line, double sentiment) {

        List<String> strings = this.createListOfMatches(line);
        Set<String> ratedWords = new HashSet<>();

        for (String str : strings) {

            str = str.toLowerCase();
            if (this.isStopWord(str)) {
                continue;
            }

            if (!wordAndRatings.containsKey(str)) {
                fillSortedSetsWithNewWord(str, sentiment);
                frequenciesForRating.put(str, 1);
            } else {
                if (!ratedWords.contains(str)) {
                    fillSortedSetsWithExistingWordRating(str, sentiment);
                    frequenciesForRating.put(str, frequenciesForRating.get(str) + 1);
                }
                fillSortedSetsWithExistingWordFrequency(str);
            }
            ratedWords.add(str);
        }
    }

    private void evaluateWordsFromMovieReviewReader() {

        try (BufferedReader bufferedReader = new BufferedReader(reviewsIn)) {

            String line = bufferedReader.readLine();
            double sentiment;

            while (line != null) {
                String[] arr = line.split(" ", 2);
                sentiment = Double.parseDouble(arr[0]);
                processWordsFromLine(arr[1], sentiment);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MovieReviewSentimentAnalyzer(Reader stopwordsIn, Reader reviewsIn, Writer reviewsOut) {

        this.stopwordsIn = stopwordsIn;
        this.reviewsIn = reviewsIn;
        this.reviewsOut = reviewsOut;

        this.ratingsAndWord = new TreeMap<>();
        this.frequenciesAndWord = new TreeMap<>();

        this.wordAndFrequencies = new HashMap<>();
        this.wordAndRatings = new HashMap<>();
        this.frequenciesForRating = new HashMap<>();

        this.stopwords = new HashSet<>();
        fillStoppers();

        evaluateWordsFromMovieReviewReader();
    }

    @Override
    public double getReviewSentiment(String review) {
        List<String> list = createListOfMatches(review);
        double sum = 0;
        int counter = 0;
        for (String word : list) {
            word = word.toLowerCase();
            if (wordAndRatings.containsKey(word)) {
                sum += wordAndRatings.get(word);
                counter += 1;
            }
        }
        if (counter == 0) {
            return -1;
        }
        return sum / (double) counter;
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        int rating = (int) Math.round(getReviewSentiment(review));
        String s = switch (rating) {
            case 0 -> "negative";
            case 1 -> "somewhat negative";
            case 2 -> "neutral";
            case 3 -> "somewhat positive";
            case 4 -> "positive";
            default -> "unknown";
        };

        return s;
    }

    @Override
    public double getWordSentiment(String word) {

        word = word.toLowerCase();
        Matcher matcher = PATTERN_WORDS.matcher(word);
        if (!matcher.find()) {
            return -1;
        }

        if (wordAndRatings.containsKey(word)) {
            return wordAndRatings.get(word);
        } else {
            return -1;
        }
    }

    @Override
    public int getWordFrequency(String word) {

        word = word.toLowerCase();
        if (stopwords.contains(word)) {
            return -1;
        }
        if (wordAndFrequencies.containsKey(word)) {
            return wordAndFrequencies.get(word);
        }
        return 0;
    }

    @Override
    public List<String> getMostFrequentWords(int n) {
        return getMostNthElementsFromSet(frequenciesAndWord, n, true);
    }

    @Override
    public List<String> getMostPositiveWords(int n) {
        return getMostNthElementsFromSet(ratingsAndWord, n, true);
    }

    @Override
    public List<String> getMostNegativeWords(int n) {
        return getMostNthElementsFromSet(ratingsAndWord, n, false);
    }

    @Override
    public boolean appendReview(String review, int sentiment) {
        if (sentiment < SENTIMENT_MIN || sentiment > SENTIMENT_MAX) {
            throw new IllegalArgumentException("Incorrect sentimental");
        }
        if (review == null || review.isEmpty() || review.isBlank()) {
            throw new IllegalArgumentException("Incorrect review");
        }
        processWordsFromLine(review, sentiment);
        try {
            String str = sentiment + ' ' + review + System.lineSeparator();
            reviewsOut.write(str);
            reviewsOut.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int getSentimentDictionarySize() {
        return wordAndRatings.size();
    }

    @Override
    public boolean isStopWord(String word) {
        return stopwords.contains(word);
    }
}
