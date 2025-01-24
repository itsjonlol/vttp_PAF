package vttp.paf.day27ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp.paf.day27ws.model.Review;
import vttp.paf.day27ws.service.ReviewService;


@Controller
public class ReviewController {
    
    @Autowired
    ReviewService reviewService;
    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView mav = new ModelAndView("home");
        Review review = new Review();
        mav.addObject("review",review);
        return mav;
    }
    

    // @PostMapping("/review")
    // public ModelAndView postReview(@ModelAttribute Review review) {
    //     //alternatively can add exceptions
    //     ModelAndView mav = new ModelAndView();
    //     if (!reviewService.checkIfGameExists(review.getId())) {
            
    //         mav.setStatus(HttpStatusCode.valueOf(404));
    //         mav.setViewName("not-found");
    //     }
    //     mav.setViewName(viewName);
    //     reviewService.insertReview(review);
    //     return ResponseEntity.status(200).header("Content-Type", "application/json").body(review);
    // }

}
