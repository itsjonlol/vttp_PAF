package vttp_paf_day24l.vttp_paf_day24l.model;

import java.util.Date;

public class TestDate {
    private Integer id;
    private String name;
    private Date date;
    private Date dateTime;
    // @JsonFormat(pattern="EEE, dd MMM yyyy HH:mm:ss zzz")
    private Date timeStamp;

    public TestDate() {

    }

    

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public Date getDate() {
        return date;
    }



    public void setDate(Date date) {
        this.date = date;
    }



    public Date getDateTime() {
        return dateTime;
    }



    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }



    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }


}
