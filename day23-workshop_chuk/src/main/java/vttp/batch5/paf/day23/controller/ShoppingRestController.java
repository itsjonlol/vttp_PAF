package vttp.batch5.paf.day23.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.paf.day23.model.LineItem;
import vttp.batch5.paf.day23.model.PurchaseOrder;
import vttp.batch5.paf.day23.service.ShoppingService;


@RestController
@RequestMapping("/api")
public class ShoppingRestController {

    @Autowired
    ShoppingService shoppingService;
    
    @PutMapping("/purchaseorder")
    public ResponseEntity<?> purchaseOrder(@RequestBody String requestBodyString) {
        // System.out.println(entry.getFirst("name"));
        InputStream is = new ByteArrayInputStream(requestBodyString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject bodyJsonObject = reader.readObject();
        String username = bodyJsonObject.getString("name");
        String address = bodyJsonObject.getString("address");
        String deliveryDateString = bodyJsonObject.getString("deliveryDate");
        JsonArray bodyJsonArray = bodyJsonObject.getJsonArray("lineItems");
        System.out.println(username);
        System.out.println(address);
        System.out.println(deliveryDateString);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<LineItem> lineItems = new ArrayList<>();
        
        purchaseOrder.setUsername(username);
        purchaseOrder.setAddress(address);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date deliveryDate = sdf.parse(deliveryDateString);
            purchaseOrder.setDeliveryDate(deliveryDate);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        

        for (int i = 0; i< bodyJsonArray.size();i++) {
            JsonObject items = bodyJsonArray.getJsonObject(i);
            String name = items.getString("name");
            Integer quantity = items.getInt("quantity");
            Float unitPrice = (float) items.getJsonNumber("unitPrice").doubleValue();
            lineItems.add(new LineItem(name,quantity,unitPrice));
            purchaseOrder.setLineItems(lineItems);
            
            System.out.println(name);
            System.out.println(quantity);
            System.out.println(unitPrice);

        }
        shoppingService.addShopping(purchaseOrder);
        Map<String,String> successMessage = new HashMap<>();
        successMessage.put("Message", "Operation sucessful");
        // @RequestBody MultiValueMap<String,String> entry
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(successMessage);
    }
   
   
}
