package vttp.batch5.paf.day23.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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


@RestController
@RequestMapping("/api")
public class ShoppingRestController {
    
    @PutMapping("/purchaseorder")
    public ResponseEntity<?> purchaseOrder(@RequestBody String requestBodyString) {
        // System.out.println(entry.getFirst("name"));
        InputStream is = new ByteArrayInputStream(requestBodyString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject bodyJsonObject = reader.readObject();
        String username = bodyJsonObject.getString("name");
        String address = bodyJsonObject.getString("address");
        String deliveryDate = bodyJsonObject.getString("deliveryDate");
        JsonArray bodyJsonArray = bodyJsonObject.getJsonArray("lineItems");
        System.out.println(username);
        System.out.println(address);
        System.out.println(deliveryDate);

        for (int i = 0; i< bodyJsonArray.size();i++) {
            JsonObject items = bodyJsonArray.getJsonObject(i);
            String name = items.getString("name");
            Integer quantity = items.getInt("quantity");
            Float unitPrice = (float) items.getJsonNumber("unitPrice").doubleValue();
            
            System.out.println(name);
            System.out.println(quantity);
            System.out.println(unitPrice);

        }

        
        // @RequestBody MultiValueMap<String,String> entry
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body("Operation successful");
    }
   
}
