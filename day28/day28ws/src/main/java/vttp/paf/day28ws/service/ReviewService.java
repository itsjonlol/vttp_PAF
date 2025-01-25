package vttp.paf.day28ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.paf.day28ws.repo.ReviewRepo;

@Service
public class ReviewService {

    @Autowired
    ReviewRepo reviewRepo;
    
}
