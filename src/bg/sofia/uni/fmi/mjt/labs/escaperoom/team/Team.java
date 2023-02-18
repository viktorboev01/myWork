package bg.sofia.uni.fmi.mjt.escaperoom.team;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

public class Team implements Ratable {

    private String name;
    private int rating;
    private TeamMember[] members;

    private Team(String name, TeamMember[] members) {
        this.members = members;
        this.name = name;
        rating = 0;
    }

    public static Team of(String name, TeamMember[] members) {
        return new Team(name, members);
    }

    public void updateRating(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("In method updateRating from class Team the parameter is negative number");
        }
        rating += points;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getRating() {
        return rating;
    }
}
