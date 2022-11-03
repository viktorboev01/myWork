package bg.sofia.uni.fmi.mjt.escaperoom.room;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

public class EscapeRoom implements Ratable {

    private double rating;
    private Review[] reviews;
    private String name;
    private Theme theme;
    private Difficulty difficulty;
    private int maxTimeToEscape, counterReviews, counterRating, maxReviewsCount;
    private double priceToPlay;


    public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay,
                      int maxReviewsCount) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.maxTimeToEscape = maxTimeToEscape;
        this.priceToPlay = priceToPlay;
        this.maxReviewsCount = maxReviewsCount;
        rating = 0;
        counterRating = 0;
        counterReviews = 0;
        reviews = new Review[0];
    }

    public boolean equals(EscapeRoom e) {
        return this.name.equals(e.name);
    }

    @Override
    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMaxTimeToEscape() {
        return maxTimeToEscape;
    }

    public Review[] getReviews() {
        return reviews;
    }

    public void addReview(Review review) {

        Review[] temp = new Review[counterReviews + 1];
        if (counterReviews == maxReviewsCount) {
            for (int i = 0; i < reviews.length - 1; i++) {
                reviews[i] = reviews[i + 1];
            }

            reviews[reviews.length - 1] = review;
        } else {
            for (int i = 0; i < reviews.length; i++) {
                temp[i] = reviews[i];
            }
            temp[counterReviews] = review;
            reviews = temp;
            counterReviews++;
        }

        counterRating++;
        rating = (rating + review.rating()) / counterRating;
    }

}