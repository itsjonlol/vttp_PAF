package vttp.paf_day25l_producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import vttp.paf_day25l_producer.model.Order;
import vttp.paf_day25l_producer.model.Todo;

@Configuration
public class RedisConfig {
    
    @Value("${redis.topic3}")
    String orderTopic;

    @Bean("todo")
    RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac, Jackson2JsonRedisSerializer<Todo> serializer) {

        RedisTemplate<String, Todo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Todo.class);
    }
    @Bean
    public Jackson2JsonRedisSerializer<Order> jackson2JsonRedisSerializer2() {
        return new Jackson2JsonRedisSerializer<>(Order.class);
    }

    @Bean("order")
    RedisTemplate<String, Order> redisTemplate2(RedisConnectionFactory connFac,Jackson2JsonRedisSerializer<Order> serializer) {

        RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
    

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(orderTopic);
    }
}
