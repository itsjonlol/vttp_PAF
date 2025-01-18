package vttp.batch5.paf.day23.model;

public class LineItem {
    
    private Integer purchaseId;
    private String name;
    private Integer quantity;
    private Float unitPrice;

    public LineItem() {

    }

    
    
    public LineItem(String name, Integer quantity, Float unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Float getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    
}
