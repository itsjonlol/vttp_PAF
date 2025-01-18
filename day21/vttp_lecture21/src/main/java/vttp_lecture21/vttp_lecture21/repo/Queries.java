package vttp_lecture21.vttp_lecture21.repo;

public class Queries {
    // public static final String SQL_SELECT_GAME = """
    //             SELECT * FROM game LIMIT 10
    //         """;

    public static final String SQL_SELECT_GAME_LIMIT = """
                SELECT * FROM game LIMIT ?
            """;
}
