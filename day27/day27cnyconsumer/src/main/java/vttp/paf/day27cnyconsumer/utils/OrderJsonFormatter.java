package vttp.paf.day27cnyconsumer.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import vttp.paf.day27cnyconsumer.models.LineItem;
import vttp.paf.day27cnyconsumer.models.PurchaseOrder;

@Component
public class OrderJsonFormatter {
    public static PurchaseOrder JsonToPojo(JsonObject data) {
        PurchaseOrder po = new PurchaseOrder();

        po.setPoId(data.getString("poId"));
        po.setName(data.getString("name"));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        po.setAddress(data.getString("address"));
        
        try {
            po.setDeliveryDate(df.parse(data.getString("deliveryDate")));
        } catch (Exception ex) {
            po.setDeliveryDate(new Date());
        }
        JsonArray jsonArray = data.getJsonArray("lineItems");
        List<LineItem> lineItems = new ArrayList<>();
        for (int i = 0; i<jsonArray.size(); i++) {
            JsonObject jo = jsonArray.getJsonObject(i);
            LineItem li = new LineItem();
            li.setName(jo.getString("name"));
            li.setQuantity(jo.getInt("quantity"));
            li.setUnitPrice((float)jo.getJsonNumber("unitPrice").doubleValue());
            lineItems.add(li);

        }
        // List<LineItem> lineItems = toLineItem(jsonArray);
        po.setLineItems(lineItems);
        return po;
        
    } 
    public static List<LineItem> toLineItem(JsonArray arr) {
        return  arr.stream()
        .map(JsonValue::asJsonObject)
        .map(jo -> {
            LineItem li = new LineItem();
            li.setName(jo.getString("name"));
            li.setQuantity(jo.getInt("quantity"));
            li.setUnitPrice((float)jo.getJsonNumber("unitPrice").doubleValue());
            return li;
        }).toList();
    }
}

// public static PurchaseOrder toPurchaseOrder(String payload) {

//     JsonReader reader = Json.createReader(new StringReader(payload));
//     JsonObject jo = reader.readObject();

//     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

//     PurchaseOrder po = new PurchaseOrder();
//     po.setPoId(jo.getString("poId", ""));
//     po.setName(jo.getString("name"));
//     po.setAddress(jo.getString("address"));
//     try {
//         po.setDeliveryDate(df.parse(jo.getString("deliveryDate")));
//     } catch (Exception ex) {
//         po.setDeliveryDate(new Date());
//     }
//     po.setLineItems(toLineItem(jo.getJsonArray("lineItems")));
    

//     return po;
// }

// public static List<LineItem> toLineItem(JsonArray arr) {

//     return  arr.stream()
//         .map(JsonValue::asJsonObject)
//         .map(jo -> {
//             LineItem li = new LineItem();
//             li.setName(jo.getString("name"));
//             li.setQuantity(jo.getInt("quantity"));
//             li.setUnitPrice((float)jo.getJsonNumber("unitPrice").doubleValue());
//             return li;
//         }).toList();

// }


// @Component
// public class OrderJsonFormatter {
    
//     public static String pojoToJson(PurchaseOrder po){
        
//         JsonArrayBuilder lineItemsArrayBuilder = Json.createArrayBuilder();

//         for (LineItem li : po.getLineItems()) {
//             JsonObject orderDetailJObject = Json.createObjectBuilder()
//                                             .add("name",li.getName())
//                                             .add("quantity", li.getQuantity())
//                                             .add("unitPrice", li.getUnitPrice())
//                                             .build();
//             lineItemsArrayBuilder.add(orderDetailJObject);
//         }
//         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//         String formattedDate = dateFormat.format(po.getDeliveryDate());
//         JsonObject jsonObject = Json.createObjectBuilder()
//                                     .add("poId",po.getPoId())
//                                     .add("name", po.getName())
//                                     .add("address", po.getAddress())
//                                     .add("deliveryDate", formattedDate)
//                                     .build();

//         return jsonObject.toString();
        
//     }
// }