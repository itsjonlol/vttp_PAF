package vttp_paf_day24l.vttp_paf_day24l.model;

public class Book {
    

    private int id; //auto-increment
    private String title; //varchar(255)
    private int quantity;
    private Boolean isActive;

    public Book() {

    }

    

    public Book(int id, String title, int quantity, boolean isActive) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.isActive = isActive;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    

}
