package bg.sofia.uni.fmi.mjt.escaperoom;

import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.PlatformCapacityExceededException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.RoomAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.escaperoom.exception.TeamNotFoundException;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Difficulty;
import bg.sofia.uni.fmi.mjt.escaperoom.room.EscapeRoom;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Review;
import bg.sofia.uni.fmi.mjt.escaperoom.room.Theme;
import bg.sofia.uni.fmi.mjt.escaperoom.team.Team;
import bg.sofia.uni.fmi.mjt.escaperoom.team.TeamMember;

import java.time.LocalDateTime;


public class EscapeRoomPlatform implements EscapeRoomPortalAPI, EscapeRoomAdminAPI {

    private int counterRooms, maxCapacity;
    private Team[] teams;
    private EscapeRoom[] escapeRooms;

    private void checkIsStringNull(String text) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            throw new IllegalArgumentException("A string has null value");
        }
    }

    private void checkIsRoomNull(EscapeRoom room) {
        if (room == null) {
            throw new IllegalArgumentException("A room has null value");
        }
        checkIsStringNull(room.getName());
    }

    private void checkIsTeamNull(Team team) {
        if (team == null || team.getName() == null) {
            throw new IllegalArgumentException("A team has null value");
        }
    }

    public EscapeRoomPlatform(Team[] teams, int maxCapacity) {
        this.teams = teams;
        counterRooms = 0;
        escapeRooms = new EscapeRoom[0];
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void addEscapeRoom(EscapeRoom room) throws RoomAlreadyExistsException {

        checkIsRoomNull(room);

        if (counterRooms == maxCapacity) {
            throw new PlatformCapacityExceededException("In method addEscapeRoom the platform reaches its maxCapacity");
        }

        EscapeRoom[] temp = new EscapeRoom[counterRooms + 1];
        for (EscapeRoom r : escapeRooms) {
            if (r.equals(room)) {
                throw new RoomAlreadyExistsException("The room already exists in the platform");
            }
        }

        for (int i = 0; i < counterRooms; i++) {
            temp[i] = escapeRooms[i];
        }
        temp[counterRooms] = room;
        escapeRooms = temp;
        counterRooms++;
    }

    @Override
    public void removeEscapeRoom(String roomName) throws RoomNotFoundException {

        checkIsStringNull(roomName);
        if (counterRooms == 0) {
            throw new RoomNotFoundException("In method removeEscapeRoom the room wasn't found");
        }

        EscapeRoom[] temp = new EscapeRoom[counterRooms - 1];
        for (int i = 0; i < counterRooms; i++) {

            if (escapeRooms[i].getName().equals(roomName)) {
                for (int j = i + 1; j < counterRooms; j++, i++) {
                    temp[i] = escapeRooms[j];
                }
                escapeRooms = temp;
                break;
            } else if (i == counterRooms - 1) {
                throw new RoomNotFoundException("In method removeEscapeRoom the room wasn't found");
            }

            temp[i] = escapeRooms[i];
        }
        counterRooms--;
    }

    @Override
    public EscapeRoom[] getAllEscapeRooms() {
        return escapeRooms;
    }

    @Override
    public void registerAchievement(String roomName, String teamName, int escapeTime)
            throws RoomNotFoundException, TeamNotFoundException {

        checkIsStringNull(roomName);
        checkIsStringNull(teamName);

        for (Team t : teams) {

            if (t != null && t.getName().equals(teamName)) {
                for (EscapeRoom e : escapeRooms) {

                    if (e.getName().equals(roomName)) {
                        if (escapeTime <= 0 || escapeTime > e.getMaxTimeToEscape()) {
                            throw new IllegalArgumentException("In method registerAchievement from class " +
                                    "EscRoomPlat the parameter is negative number");
                        }

                        int bonusFast = 0;

                        if (escapeTime * 2 <= e.getMaxTimeToEscape()) {
                            bonusFast = 2;
                        } else if (escapeTime * 4 <= e.getMaxTimeToEscape() * 3) {
                            bonusFast = 1;
                        }

                        t.updateRating(bonusFast + e.getDifficulty().getRank());
                        return;
                    }
                }
                throw new RoomNotFoundException("The method registerAchievement found the team, but not the room");
            }
        }
        throw new TeamNotFoundException("The method registerAchievement didn't found the team");

    }

    @Override
    public EscapeRoom getEscapeRoomByName(String roomName) throws RoomNotFoundException {

        checkIsStringNull(roomName);

        for (EscapeRoom e : escapeRooms) {
            if (e.getName().equals(roomName)) {
                return e;
            }
        }
        throw new RoomNotFoundException("The method getEscapeRoomByName didn't found the room");
    }

    @Override
    public void reviewEscapeRoom(String roomName, Review review) throws RoomNotFoundException {
        getEscapeRoomByName(roomName).addReview(review);
    }

    @Override
    public Review[] getReviews(String roomName) throws RoomNotFoundException {
        return getEscapeRoomByName(roomName).getReviews();
    }

    @Override
    public Team getTopTeamByRating() {

        if (teams == null || teams.length == 0) {
            return null;
        }
        Team top = null;
        for (Team t : teams) {
            if (t != null && (top == null || t.getRating() > top.getRating())) {
                top = t;
            }
        }
        return top;
    }

}
