package vttp.paf_day25l_consumer.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class OrderDetails {
    
    private Integer detailsId;
    private String product;
    private Float unitPrice;
    private Float discount;
    private Integer quantity;
    

    public OrderDetails() {

    }
    
    
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public Float getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Float getDiscount() {
        return discount;
    }
    public void setDiscount(Float discount) {
        this.discount = discount;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Integer detailsId) {
        this.detailsId = detailsId;
    }

   
    
    

}
