package vttp.paf.day28ws.model;

import java.util.Date;
import java.util.List;

public class GameDto {
    private String name; // Name of the game
    private int year; // Year the game was published
    private int rank; // Ranking of the game
    private double average; // Average rating of the game
    private int users_rated; // Number of users who rated the game
    private String thumbnail; // URL of the game's thumbnail image
    private List<String> reviews; // List of review URLs
    private int game_id; // Unique ID of the game
    private Date timestamp; // Timestamp of the response
    //@DateTimeFormat(iso = ISO.DATE_TIME) private Date date;

    public GameDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getUsers_rated() {
        return users_rated;
    }

    public void setUsers_rated(int users_rated) {
        this.users_rated = users_rated;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    
}
