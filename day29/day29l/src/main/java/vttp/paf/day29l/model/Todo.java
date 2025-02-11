package vttp.paf.day29l.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Todo {
    
    private Integer id;
    private String taskName;
    @JsonFormat(pattern = "yyyy-MM-dd")
   
    private Date dueDate;

    public Todo() {
        this.dueDate = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", taskName=" + taskName + ", dueDate=" + dueDate + "]";
    }
    
    
    
}
