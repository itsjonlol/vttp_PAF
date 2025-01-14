package vttp.paf_day25l_consumer.model;

import java.util.List;

public class Order {
    private int id;
    private String fullName;
    private List<OrderDetails> lineItems;

    public Order() {

    }
    

    public Order(int id, String fullName, List<OrderDetails> lineItems) {
        this.id = id;
        this.fullName = fullName;
        this.lineItems = lineItems;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<OrderDetails> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderDetails> lineItems) {
        this.lineItems = lineItems;
    }

    
}
