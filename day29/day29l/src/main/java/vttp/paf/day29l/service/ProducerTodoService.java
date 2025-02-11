package vttp.paf.day29l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp.paf.day29l.model.Todo;
import vttp.paf.day29l.utils.TodoJsonFormatter;

@Service
public class ProducerTodoService {
    
    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String,String> redisTemplate;

    public void sendMessage(Todo todo) {
        String toDoJsonString = TodoJsonFormatter.pojoToJson(todo).toString();
        //publish to subscribers
        redisTemplate.convertAndSend("notifications", toDoJsonString);                                                                                                        
    }
    
    public void sendQueue(Todo todo) {
        String toDoJsonString = TodoJsonFormatter.pojoToJson(todo).toString();
        redisTemplate.opsForList().leftPush("messages", toDoJsonString);
        
    }

    

}
