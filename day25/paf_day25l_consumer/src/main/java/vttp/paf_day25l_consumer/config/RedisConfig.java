package vttp.paf_day25l_consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import vttp.paf_day25l_consumer.model.Order;
import vttp.paf_day25l_consumer.model.Todo;
import vttp.paf_day25l_consumer.service.ConsumerService;

@Configuration
public class RedisConfig {

    @Value("${redis.topic1}")
    private String redisTopic;

    @Value("${redis.topic3}")
    String orderTopic;
    

    @Bean
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

    @Bean
    public RedisMessageListenerContainer listenerContainer(MessageListenerAdapter messageListenerAdapter,
    RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic));

        return container;
    }
    
    
    @Bean
    public MessageListenerAdapter listenerAdapter(ConsumerService redisConsumerService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(redisConsumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));
        return adapter;
    }

    
    @Bean
    RedisTemplate<String,Order> RedisTemplate2
    (RedisConnectionFactory connFac,Jackson2JsonRedisSerializer<Todo> serializer) {
        
        RedisTemplate<String,Order> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connFac);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.afterPropertiesSet();


        return redisTemplate;
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(orderTopic);
    }

    //subscriber needs the listener
    private MessageListener messageListener;
    private RedisConnectionFactory redisConnectionFactory;

    // @Bean
    // public MessageListener  messag


}
