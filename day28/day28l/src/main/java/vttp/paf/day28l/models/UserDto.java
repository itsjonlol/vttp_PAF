package vttp.paf.day28l.models;

import java.util.List;

public class UserDto {
    
    private String _id;
    private List<Review> userReviews;

    public UserDto() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }
    
}
