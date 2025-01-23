package vttp.paf.day26_ws.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer usersRated;
    private String url;
    private String image;
    private String timestamp;
    

    public Game() {
        LocalDateTime now = LocalDateTime.now();

        // Define the format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time
        String formattedNow = now.format(formatter);
        this.timestamp = formattedNow;
    }

    public Game(Integer gid, String name, Integer year, Integer ranking, Integer usersRated, String url, String image) {
        this.gid = gid;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.usersRated = usersRated;
        this.url = url;
        this.image = image;
    }



    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getUsersRated() {
        return usersRated;
    }

    public void setUsersRated(Integer usersRated) {
        this.usersRated = usersRated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    

}
