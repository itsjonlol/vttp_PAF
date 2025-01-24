package vttp.paf.day27ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day27ws.model.UpdateReview;
import vttp.paf.day27ws.repo.ReviewRepo;

@Service
public class UpdateReviewService {
    
    @Autowired
    ReviewRepo reviewRepo;

    public Boolean checkIfReviewExist(String id) {
        return reviewRepo.checkIfReviewExist(id);
    }
    public Boolean updateReview(UpdateReview updateReview,String id) {
        return reviewRepo.updateReview(updateReview, id);
    }
    
}
