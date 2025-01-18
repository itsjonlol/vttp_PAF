package vttp.day23ws.model;

import java.util.Date;

public class Details {
    
    private Integer orderId;
    
    private Date orderDate;
    private Integer customerid;
    private Float totalFinalPrice;
    private Float totalCostPrice;

    public Details() {

    }
    

    public Details(Integer orderId, Integer customerid, Float totalFinalPrice, Float totalCostPrice) {
        this.orderId = orderId;
        this.customerid = customerid;
        this.totalFinalPrice = totalFinalPrice;
        this.totalCostPrice = totalCostPrice;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public Integer getCustomerid() {
        return customerid;
    }
    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }
    public Float getTotalFinalPrice() {
        return totalFinalPrice;
    }
    public void setTotalFinalPrice(Float totalFinalPrice) {
        this.totalFinalPrice = totalFinalPrice;
    }
    public Float getTotalCostPrice() {
        return totalCostPrice;
    }
    public void setTotalCostPrice(Float totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }


    // public Timestamp getOrderTimestamp() {
    //     return orderTimestamp;
    // }


    // public void setOrderTimestamp(Timestamp orderTimestamp) {
    //     this.orderTimestamp = orderTimestamp;
    // }

    
}
