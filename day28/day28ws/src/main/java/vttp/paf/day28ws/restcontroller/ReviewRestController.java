package vttp.paf.day28ws.restcontroller;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.paf.day28ws.model.GameDto;
import vttp.paf.day28ws.model.GameReviewDto;
import vttp.paf.day28ws.repo.ReviewRepo;
import vttp.paf.day28ws.service.ReviewService;



@RestController
@RequestMapping("/api")
public class ReviewRestController {
    
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepo reviewRepo;

    @GetMapping("/game/{game_id}/reviews")
    public ResponseEntity<?> getMethodName(@PathVariable("game_id") Integer gid) {
        GameDto gameDto = reviewRepo.getGameReviews(gid);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameDto);
    }
    //test doc
    @GetMapping("/game/{game_id}/reviews2")
    public ResponseEntity<?> getMethodName2(@PathVariable("game_id") Integer gid) {
        Document gameDoc = reviewRepo.getGameDoc(gid);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(gameDoc);
    }
    @GetMapping("games/highest2")
    public ResponseEntity<?> getHighestRatedGames2() {
        Document document = reviewRepo.getHighestRatedGamesDocuments();
        //method 1) build json response
        //2) show the document directly
        //3) build model response
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(document);
    }

    @GetMapping("games/highest")
    public ResponseEntity<?> getHighestRatedGames() {
        List<GameReviewDto> games = reviewRepo.getHighestRatedGames();
        //method 1) build json response
        //2) show the document directly
        //3) build model response
        JsonObject response = buildResponse("highest", games); // Build the JSON response
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(response.toString());
    }
    private JsonObject buildResponse(String ratingType, List<GameReviewDto> games) {
        // Create a JsonObjectBuilder to build the response
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        // Add the "rating" field
        responseBuilder.add("rating", ratingType);

        // Build the "games" array
        JsonArrayBuilder gamesArrayBuilder = Json.createArrayBuilder();
        for (GameReviewDto game : games) {
            JsonObjectBuilder gameBuilder = Json.createObjectBuilder()
                    .add("_id", game.get_id())
                    .add("name", game.getName())
                    .add("rating", game.getRating())
                    .add("user", game.getUser())
                    .add("comment", game.getComment())
                    .add("review_id", game.getReview_id());
            gamesArrayBuilder.add(gameBuilder);
        }

        // Add the "games" array to the response
        responseBuilder.add("games", gamesArrayBuilder);

        // Add the "timestamp" field
        responseBuilder.add("timestamp", new Date().toString());

        // Build the final JsonObject
        return responseBuilder.build();
    }
    
    
}
