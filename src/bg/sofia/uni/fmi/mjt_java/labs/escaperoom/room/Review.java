package bg.sofia.uni.fmi.mjt.escaperoom.room;

public record Review(int rating, String reviewText) {
    public Review {
        if (rating > 10 || rating < 0) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        if (reviewText == null) {
            throw new IllegalArgumentException("reviewText in Review is null");
        }
        if (reviewText.length() > 200) {
            throw new IllegalArgumentException("Text should be less than 200 characters");
        }
    }
}
