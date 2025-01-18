package vttp_day24ws.vttp_day24ws.model.exceptions;

import java.util.Date;


public class ErrorMessage {
    

    private int status;
    private String message;
    private Date timeStamp;
    private String endPoint;
    
    public ErrorMessage() {

    }

    public ErrorMessage(int status, String message, Date timeStamp, String endPoint) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
        this.endPoint = endPoint;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    


}
