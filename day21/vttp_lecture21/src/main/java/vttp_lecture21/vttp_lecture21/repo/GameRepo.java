package vttp_lecture21.vttp_lecture21.repo;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_lecture21.vttp_lecture21.model.Game;
import static vttp_lecture21.vttp_lecture21.repo.Queries.SQL_SELECT_GAME_LIMIT;

@Repository
public class GameRepo {


    @Autowired
    private JdbcTemplate template;
    
    public List<Game> getGames() {
        return this.getGames(10);

    }

    public List<Game> getGames(int limit) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_GAME_LIMIT,limit);
        List<Game> results = new LinkedList<>();
        //will return true if there are records
        while (rs.next()) { // to move the cursor to read the first record
            results.add(Game.toMovie(rs));
            
        }
        return results;

    }
    
}
