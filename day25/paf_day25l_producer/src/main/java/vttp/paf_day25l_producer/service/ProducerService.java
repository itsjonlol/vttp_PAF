package vttp.paf_day25l_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import vttp.paf_day25l_producer.model.Order;
import vttp.paf_day25l_producer.model.Todo;

@Service
public class ProducerService {
    
    @Autowired @Qualifier("todo")
    RedisTemplate<String,Todo> redisTemplate;

    @Autowired
    RedisTemplate<String,Order> redisTemplate2;

    @Value("${redis.topic1}")
    private String topic1;

    @Autowired
    ChannelTopic channelTopic;

    public void sendMessage(Todo todo) {
        redisTemplate.convertAndSend(topic1, todo);                                                                                                        
    }

    public Long publish(Order order) {
        return redisTemplate2.convertAndSend(channelTopic.getTopic(), order);
    }
}
