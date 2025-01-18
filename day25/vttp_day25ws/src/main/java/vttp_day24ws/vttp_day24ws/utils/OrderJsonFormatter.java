package vttp_day24ws.vttp_day24ws.utils;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp_day24ws.vttp_day24ws.model.Order;
import vttp_day24ws.vttp_day24ws.model.OrderDetails;

@Component
public class OrderJsonFormatter {
    
    public static String pojoToJson(Order order){
        
        JsonArrayBuilder lineItemsArrayBuilder = Json.createArrayBuilder();

        for (OrderDetails od : order.getOrderDetailsList()) {
            JsonObject orderDetailJObject = Json.createObjectBuilder()
                                            .add("product", od.getProduct())
                                            .add("unitPrice", od.getUnitPrice())
                                            .add("discount", od.getDiscount())
                                            .add("quantity", od.getQuantity())
                                            .build();
            lineItemsArrayBuilder.add(orderDetailJObject);
        }

        JsonObject jsonObject = Json.createObjectBuilder()
                                    .add("customerName", order.getCustomerName())
                                    .add("orderDate", order.getOrderDate().toString())
                                    .add("shipAddress", order.getShipAddress())
                                    .add("notes", order.getNotes())
                                    .add("tax", order.getTax())
                                    .add("orderDetailsList", lineItemsArrayBuilder.build())
                                    .build();

        return jsonObject.toString();
        
    }
}
