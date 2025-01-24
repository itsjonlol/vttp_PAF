package vttp.paf.day27ws.model;

import java.time.LocalDateTime;

public class UpdateReview {
    private String comment;
    private Integer rating;
    private LocalDateTime posted;

    public UpdateReview() {
        this.posted = LocalDateTime.now();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    
}
