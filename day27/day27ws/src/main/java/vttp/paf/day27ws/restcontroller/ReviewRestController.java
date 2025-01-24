package vttp.paf.day27ws.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day27ws.model.Review;
import vttp.paf.day27ws.model.UpdateReview;
import vttp.paf.day27ws.service.ReviewService;
import vttp.paf.day27ws.service.UpdateReviewService;




@RestController
@RequestMapping("/api")
public class ReviewRestController {
    
    @Autowired
    ReviewService reviewService;
    @Autowired
    UpdateReviewService updateReviewService;

    @PostMapping("/review")
    public ResponseEntity<?> postReviewApi(@RequestBody Review review) {
        //alternatively can add exceptions
        if (!reviewService.checkIfGameExists(review.getId())) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("ErrorMessage","gid doesn't exist");
            return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);
        }
        reviewService.insertReview(review);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(review);
    }

    @PutMapping("/review/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable("review_id") String id, @RequestBody UpdateReview updateReview) {
        if (!updateReviewService.checkIfReviewExist(id)) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("ErrorMessage","review id doesn't exist");
            return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);
        }
        Boolean updated = updateReviewService.updateReview(updateReview, id);
        if (!updated) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("ErrorMessage","Update failed");
            return ResponseEntity.status(400).header("Content-Type", "application/json").body(errorMessage);
        }
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(updateReview);
    }

    @GetMapping("/review/{review_id}/history")
    public ResponseEntity<?> getReviewHistory(@PathVariable("review_id") String id) {
        Document document = reviewService.getReviewHistory(id);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(document);
    }

    @GetMapping("/review/{review_id}")
    public ResponseEntity<?> getLatestReview(@PathVariable("review_id") String id) {
        Document document = reviewService.getLatestReview(id);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(document);
    }
    
    
}
