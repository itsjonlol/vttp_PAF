package vttp_lecture21.vttp_lecture21.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp_lecture21.vttp_lecture21.model.Game;
import vttp_lecture21.vttp_lecture21.repo.GameRepo;

@Service
public class GameService {
    

    @Autowired
    private GameRepo gameRepo;

    // public List<Game> getGames() {
    //     return gameRepo.getGames();

    // }

    public List<Game> getGames(int limit) {
        return gameRepo.getGames(limit);
    }
    
}
