package vttp.paf_day25l_consumer.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class Order {
    private Integer orderId;
    private Date orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private Float tax;
    @JsonProperty("orderDetailsList")
    private List<OrderDetails> orderDetailsList;
    
    
    public Order() {
        
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


    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getShipAddress() {
        return shipAddress;
    }


    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }


    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }


    public Float getTax() {
        return tax;
    }


    public void setTax(Float tax) {
        this.tax = tax;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    // @Override
    // public String toString() {
    //     return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", customerName=" + customerName
    //             + ", shipAddress=" + shipAddress + ", notes=" + notes + ", tax=" + tax + ", orderDetailsList="
    //             + orderDetailsList + "]";
    // }


    
}
