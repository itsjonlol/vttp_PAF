package vttp.batch5.paf.day23.model;

import java.util.Date;
import java.util.List;

public class PurchaseOrder {
    
    private Integer purchaseId;
    private String username;
    private String address;
    private Date deliveryDate;
    private List<LineItem> lineItems;
    
    public Integer getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public List<LineItem> getLineItems() {
        return lineItems;
    }
    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    

    

}
