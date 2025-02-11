package vttp.paf.day29l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import vttp.paf.day29l.model.Order;
import vttp.paf.day29l.utils.OrderJsonFormatter;

@Service
public class ProducerOrderService {
    
    @Autowired @Qualifier("myredis")
    RedisTemplate<String,String> redisTemplate;
    public void publish(Order order) {

        JsonObject orderJson = OrderJsonFormatter.pojoToJson(order);
        
        redisTemplate.convertAndSend("notifications", orderJson.toString());
    }
}
