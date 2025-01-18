package vttp_lecture21.vttp_lecture21.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Game {
    
    private int gameId;
    private String name;
    private int year;
    private int ranking;
    private int usersRated;
    private String url;
    private String image;

    public Game() {

    }


    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
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
    public int getRanking() {
        return ranking;
    }
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    public int getUsersRated() {
        return usersRated;
    }
    public void setUsersRated(int usersRated) {
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
    
    public static Game toMovie(SqlRowSet rs) {
        Game g = new Game();
        g.setYear(rs.getInt("year"));
        g.setUrl(rs.getString("url"));
        g.setName(rs.getString("name"));
        g.setGameId(rs.getInt("gid"));
        g.setImage(rs.getString("image"));
        g.setRanking(rs.getInt("ranking"));
        g.setUsersRated(rs.getInt("users_rated"));
        return g;
    }

}
