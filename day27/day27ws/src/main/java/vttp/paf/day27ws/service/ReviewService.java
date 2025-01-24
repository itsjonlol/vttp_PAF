package vttp.paf.day27ws.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day27ws.model.Review;
import vttp.paf.day27ws.repo.ReviewRepo;

@Service
public class ReviewService {
    
    @Autowired
    ReviewRepo reviewRepo;

    public Boolean checkIfGameExists(Integer id) {
        return reviewRepo.checkIfGameExist(id);
    }

    public Boolean insertReview(Review review) {
        String gameName = reviewRepo.getGameName(review.getId());
        review.setName(gameName);
        
        return reviewRepo.insertReview(review);
    }

    public Document getReviewHistory(String id) {
        return reviewRepo.getReviewHistory(id);
    }

    public Document getLatestReview(String id) {
        return reviewRepo.getLatestReview(id);
    }
}
