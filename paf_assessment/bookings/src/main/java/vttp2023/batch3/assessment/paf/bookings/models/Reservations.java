package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Reservations {
    
    private String resvId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "field is mandatory")
    @Future(message = "Future dates only")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalDate;

    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer duration;

    private String accId;

    public Reservations() {
        this.resvId = UUID.randomUUID().toString().substring(0,8);
    }

    public String getResvId() {
        return resvId;
    }

    public void setResvId(String resvId) {
        this.resvId = resvId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }
    
    
    
    

}
