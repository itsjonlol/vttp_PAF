package vttp_day24ws.vttp_day24ws.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp_day24ws.vttp_day24ws.model.Order;

@Service
public class SubscriberService implements MessageListener {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderService orderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {



        String orderData = new String(message.getBody());
        System.out.println(orderData);
        System.out.println("onmessage");
        try {
            Order order = objectMapper.readValue(orderData, Order.class);

            orderService.createOrderRecord(order);

        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // InputStream is = new ByteArrayInputStream(orderData.getBytes());
        // JsonReader reader = Json.createReader(is);
        // JsonObject orderJsonObject = reader.readObject();
        
        
    }
    
}
