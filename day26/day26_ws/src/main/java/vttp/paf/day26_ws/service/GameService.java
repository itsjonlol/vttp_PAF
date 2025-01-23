package vttp.paf.day26_ws.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day26_ws.model.Game;
import vttp.paf.day26_ws.model.GameResponse;
import vttp.paf.day26_ws.repo.GameRepo;

@Service
public class GameService {
    
    @Autowired
    GameRepo gameRepo;

    public GameResponse getGameResponse(Integer limit, Integer offset,Boolean sortByRank) {
        return gameRepo.getAllGamesResponse(limit, offset,sortByRank);
    }

    public Optional<Game> getGameById(Integer gid) {
        return gameRepo.getGameById(gid);
    }

    
}
