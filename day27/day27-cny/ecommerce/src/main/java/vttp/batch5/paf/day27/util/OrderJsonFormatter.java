package vttp.batch5.paf.day27.util;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.paf.day27.models.LineItem;
import vttp.batch5.paf.day27.models.PurchaseOrder;

@Component
public class OrderJsonFormatter {
    
    public static String pojoToJson(PurchaseOrder po){
        
        JsonArrayBuilder lineItemsArrayBuilder = Json.createArrayBuilder();

        for (LineItem li : po.getLineItems()) {
            JsonObject orderDetailJObject = Json.createObjectBuilder()
                                            .add("name",li.getName())
                                            .add("quantity", li.getQuantity())
                                            .add("unitPrice", li.getUnitPrice())
                                            .build();
            lineItemsArrayBuilder.add(orderDetailJObject);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(po.getDeliveryDate());
        JsonObject jsonObject = Json.createObjectBuilder()
                                    .add("poId",po.getPoId())
                                    .add("name", po.getName())
                                    .add("address", po.getAddress())
                                    .add("deliveryDate", formattedDate)
                                    .add("lineItems",lineItemsArrayBuilder.build())
                                    .build();

        return jsonObject.toString();
        
    }
}
