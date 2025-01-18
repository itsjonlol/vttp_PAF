package vttp_day24ws.vttp_day24ws.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp_day24ws.vttp_day24ws.model.Order;

@Repository
public class RedisRepo {
    @Autowired
    @Qualifier("myredis")
    public RedisTemplate<String, String> template;

    @Autowired
    ObjectMapper objectMapper;

    public void pushToRepo(String appName,Order order) {

        try {
            
            // System.out.println(OrderJsonFormatter.pojoToJson(order));
            template.opsForList().rightPush(appName, objectMapper.writeValueAsString(order));
            // template.opsForList().rightPush(appName, OrderJsonFormatter.pojoToJson(order)); // if not automapping

        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> getList(String redisKey) {
        List<String> list = template.opsForList().range(redisKey,0,-1); //lrange redisKey 0 -1
        return list;
    }
}
