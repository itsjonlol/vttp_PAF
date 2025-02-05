package vttp.paf.day27ws.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Review {
//     user: <name form field>,
// rating: <rating form field>,
// comment: <comment form field>,
// ID: <game id form field>,
// posted: <date>,
// name: <The board game’s name as per ID>
    private String user;
    private Integer rating;
    private String comment;

    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String name;

    public Review() {
        this.date = LocalDate.now();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    
}


