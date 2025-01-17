package main.java.vttp_lecture22ws.vttp_lecture22ws.model;

import java.sql.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class RSVP {
    
    private Integer rsvp_id;
    private String email;
    private String phone;
    private Date confirmDate;
    private String comments;

    public RSVP() {

    }
    
    public RSVP(String email, String phone, Date confirmDate, String comments) {
        this.email = email;
        this.phone = phone;
        this.confirmDate = confirmDate;
        this.comments = comments;
    }


    public RSVP(Integer rsvp_id, String email, String phone, Date confirmDate, String comments) {
        this.rsvp_id = rsvp_id;
        this.email = email;
        this.phone = phone;
        this.confirmDate = confirmDate;
        this.comments = comments;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    

    public static RSVP toRSVP(SqlRowSet rs) {
        RSVP rsvp = new RSVP();
        rsvp.setRsvp_id(rs.getInt("rsvp_id"));
        rsvp.setEmail(rs.getString("email"));
        rsvp.setPhone(rs.getString("phone"));
        rsvp.setConfirmDate(rs.getDate("confirmdate"));
        rsvp.setComments(rs.getString("comments"));
        return rsvp;
    }

    public Integer getRsvp_id() {
        return rsvp_id;
    }

    public void setRsvp_id(Integer rsvp_id) {
        this.rsvp_id = rsvp_id;
    }
}
