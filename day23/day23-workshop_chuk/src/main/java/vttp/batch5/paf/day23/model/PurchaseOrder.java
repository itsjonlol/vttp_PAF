package vttp.batch5.paf.day23.model;

import java.util.Date;
import java.util.List;

public class PurchaseOrder {
    
    private Integer purchaseId;
    private String name;
    private String address;
    // @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    private List<LineItem> lineItems;
    
    public PurchaseOrder() {

    }
    

    public PurchaseOrder(String name, String address, Date deliveryDate, List<LineItem> lineItems) {
        this.name = name;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.lineItems = lineItems;
    }


    public Integer getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
