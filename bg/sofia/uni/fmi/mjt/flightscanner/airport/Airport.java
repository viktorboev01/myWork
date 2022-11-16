package bg.sofia.uni.fmi.mjt.flightscanner.airport;

public record Airport(String Id) {
    public Airport {
        if (Id == null || Id.isEmpty() || Id.isBlank()) {
            throw new IllegalArgumentException("An Id isn't valid");
        }
    }
}
