package vttp.paf.day26_ws.restcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day26_ws.model.Game;
import vttp.paf.day26_ws.model.GameResponse;
import vttp.paf.day26_ws.repo.GameRepo;
import vttp.paf.day26_ws.service.GameService;


@RestController
public class GameRestController {
    

    @Autowired
    GameService gameService;
    @Autowired
    GameRepo gameRepo;

    @GetMapping("/games")
    public ResponseEntity<?> getAllGames(@RequestParam(value = "limit",required = false,defaultValue="25") Integer limit,
    @RequestParam(value = "offset",required=false,defaultValue="0") Integer offset) {
        GameResponse gameResponse =  gameService.getGameResponse(limit, offset,false);

    
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameResponse);
    }
    //test returning one big document
    @GetMapping("/games2")
    public ResponseEntity<?> getAllGames2(@RequestParam(value = "limit",required = false,defaultValue="25") Integer limit,
    @RequestParam(value = "offset",required=false,defaultValue="0") Integer offset) {
        // GameResponse gameResponse =  gameService.getGameResponse(limit, offset,false);
        Document gameDocument = gameRepo.getGameDocuments(limit, offset, false);
    
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameDocument);
    }
    @GetMapping("/games/rank")
    public ResponseEntity<?> getAllGamesByRanking(@RequestParam(value = "limit",required = false,defaultValue="25") Integer limit,
    @RequestParam(value = "offset",required=false,defaultValue="0") Integer offset) {
        GameResponse gameResponse =  gameService.getGameResponse(limit, offset,true);

    
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameResponse);
    }
    @GetMapping("/game/{gid}")
    public ResponseEntity<?> getGameById(@PathVariable("gid") Integer gid) {
        Optional<Game> gameOpt = gameService.getGameById(gid);

        if (gameOpt.isPresent()) {
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameOpt.get());
        }
        Map<String,String> errorMessage = new HashMap<>();
        errorMessage.put("message","Game Id "+ gid + " not found");
        return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);

    
        
    }
    
    
    
}
