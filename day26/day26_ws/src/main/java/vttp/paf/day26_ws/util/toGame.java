package vttp.paf.day26_ws.util;

import org.bson.Document;

import vttp.paf.day26_ws.model.Game;

public class toGame {
    public static Game toGameFromDocument(Document document) {
        Game game = new Game();
        // private Integer gid;
        // private String name;
        // private Integer year;
        // private Integer ranking;
        // private Integer usersRated;
        // private String url;
        // private String image;
        game.setGid(document.getInteger("gid"));
        game.setName(document.getString("name"));
        game.setYear(document.getInteger("year"));
        game.setRanking(document.getInteger("ranking"));
        game.setUsersRated(document.getInteger("users_rated"));
        game.setUrl(document.getString("url"));
        game.setImage(document.getString("image"));
        return game;

    }
}
