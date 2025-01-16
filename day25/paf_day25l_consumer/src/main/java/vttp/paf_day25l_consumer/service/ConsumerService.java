package vttp.paf_day25l_consumer.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.paf_day25l_consumer.model.Order;

@Service
public class ConsumerService implements MessageListener {

    @Autowired
    @Qualifier("order")
    private RedisTemplate<String, Order> template;
    
  
    
    //manually parse the json-string
    @Override
    public void onMessage(Message message, byte[] pattern) {
        RestTemplate restTemplate = new RestTemplate();
       
        String orderData = new String(message.getBody());
        
        System.out.println(orderData);

        InputStream is = new ByteArrayInputStream(orderData.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject orderJsonObject = reader.readObject();
        // orderJsonObject.remove("order_")

        // Extract existing values from the original orderJsonObject
        // long dateLong = orderJsonObject.getJsonNumber("orderDate").longValue();
        // Date sqlDate = new Date(dateLong);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // Iterate over the original JsonObject and add each key-value pair
        // orderJsonObject.forEach((key, value) -> {
        //     if (key.equals("orderDate")) {
        //         builder.add(key, sdf.format(dateLong));  // Add the formatted date
        //     } else {
        //         builder.add(key, value);  // Add other fields as is
        //     }
        // });
        orderJsonObject.forEach((key, value) -> {
           
            
                builder.add(key, value);  // Add other fields as is
            
        });

        
        JsonObject updatedJsonObject = builder.build();

        //inefficient method
//         String customerName = orderJsonObject.get    String("customerName");
//         String shipAddress = orderJsonObject.getString("shipAddress");
//         String notes = orderJsonObject.getString("notes");
//         Double tax = orderJsonObject.getJsonNumber("tax").doubleValue();
//         JsonArray orderDetailsList = orderJsonObject.getJsonArray("orderDetailsList"); // Get the original orderDetailsList
//         System.out.println(orderDetailsList.size());
//         JsonArrayBuilder updatedOrderDetailsList = Json.createArrayBuilder();

//         // Iterate over the original orderDetailsList to create a new list using the index-based loop
//         for (int i = 0; i < orderDetailsList.size(); i++) {

      
//             JsonObject orderDetails = orderDetailsList.getJsonObject(i); // Get each orderDetails object
            
//             JsonObjectBuilder orderDetailsBuilder = Json.createObjectBuilder();
            
//             // Copy fields from the original orderDetails object, or modify them if needed
//             orderDetailsBuilder.add("product", orderDetails.getString("product"))
//                                .add("unitPrice", orderDetails.getJsonNumber("unitPrice"))
//                                .add("discount", orderDetails.getJsonNumber("discount"))
//                                .add("quantity", orderDetails.getInt("quantity"));
        
//             // Add the modified orderDetails object to the array
//             updatedOrderDetailsList.add(orderDetailsBuilder.build());
//         }

// // Rebuild the JsonObject with the new orderDate and the modified orderDetailsList
// JsonObject updatedOrderJsonObject = Json.createObjectBuilder()
//     .add("orderDate", sdf.format(dateLong)) // Adding the new orderDate
//     .add("customerName", customerName)
//     .add("shipAddress", shipAddress)
//     .add("notes", notes)
//     .add("tax", tax)
//     .add("orderDetailsList", updatedOrderDetailsList.build()) // Updated orderDetailsList
//     .build();

        //     using Json-P to map it back to object
        //     call the API in day 24 using RestTemplate to write to MySQL database

        String url = "http://localhost:4000/api/order";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));



        //OR CAN orderData directly
        try {
            RequestEntity<String> requestEntity = RequestEntity.post(url)
                                                           .headers(headers)
                                                           .body(updatedJsonObject.toString());
            ResponseEntity<String> responseResult = restTemplate.exchange(requestEntity,String.class);
            System.out.println(responseResult.getBody());
            
        } catch (RestClientException ex) {
            System.out.println(ex.getMessage());
        }
		

        

      

    }
}
