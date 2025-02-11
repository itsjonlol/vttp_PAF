package vttp.paf.day29l.utils;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.paf.day29l.model.Order;
import vttp.paf.day29l.model.OrderDetails;

@Component
public class OrderJsonFormatter {
    
    public static JsonObject pojoToJson(Order order){
        
        JsonArrayBuilder lineItemsArrayBuilder = Json.createArrayBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(order.getOrderDate());

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
                                    .add("orderDate", dateString)
                                    .add("shipAddress", order.getShipAddress())
                                    .add("notes", order.getNotes())
                                    .add("tax", order.getTax())
                                    .add("orderDetailsList", lineItemsArrayBuilder.build())
                                    .build();

        return jsonObject;
        
    }
}
