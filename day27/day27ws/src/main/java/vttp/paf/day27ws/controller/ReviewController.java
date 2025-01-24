package vttp.paf.day27ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import vttp.paf.day27ws.service.ReviewService;

@Controller
public class ReviewController {
    
    @Autowired
    ReviewService reviewService;
}
