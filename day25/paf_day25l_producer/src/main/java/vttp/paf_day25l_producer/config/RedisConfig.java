package vttp.paf_day25l_producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import vttp.paf_day25l_producer.model.Todo;

@Configuration
public class RedisConfig {

    @Value("${redis.topic3}")
    String orderTopic;

    @Bean("todo")
    RedisTemplate<String,Todo> RedisTemplate
    (RedisConnectionFactory connFac,Jackson2JsonRedisSerializer<Todo> serializer) {
        
        RedisTemplate<String,Todo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();


        return redisTemplate;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Todo> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(Todo.class);

    }

    @Bean("order")
    RedisTemplate<String,Todo> RedisTemplate2
    (RedisConnectionFactory connFac,Jackson2JsonRedisSerializer<Todo> serializer) {
        
        RedisTemplate<String,Todo> redisTemplate = new RedisTemplate<>();
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
