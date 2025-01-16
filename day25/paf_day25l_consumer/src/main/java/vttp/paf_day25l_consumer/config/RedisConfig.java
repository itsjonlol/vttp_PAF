package vttp.paf_day25l_consumer.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.paf_day25l_consumer.model.Order;
import vttp.paf_day25l_consumer.model.Todo;
import vttp.paf_day25l_consumer.service.ConsumerService;

@Configuration
public class RedisConfig {

    @Value("${redis.topic1}")
    private String redisTopic;

    @Value("${redis.topic3}")
    private String orderTopic;

    @Bean
    public RedisTemplate<String, Todo> redisTemplate(RedisConnectionFactory connFac,
                                                     Jackson2JsonRedisSerializer<Todo> serializer) {
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
    public RedisMessageListenerContainer listenerContainer(
            RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(redisTopic));
        return container;
    }
    //container -> set up the environment for listening to oncoming messages for specified topics
    //conn factory-> establishes connection to redis
    //messagelisteneradapter -> delegates incoming messages to the consumer service
    //patterntopic -> specifies the topic that the container listens to
    //when a message is published to the topic, the  container passes it to messagelistenerdapater 

    @Bean
    public MessageListenerAdapter messageListenerAdapter(ConsumerService consumerService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(consumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Todo.class));
        return adapter;
    }

    //when a message arrives, the adapter converts the message into a Todo object

    

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(orderTopic);
    }


    @Bean
    public RedisMessageListenerContainer listenerContainer2(
            RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(orderTopic));
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter2(ConsumerService consumerService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(consumerService);
        adapter.setSerializer(new Jackson2JsonRedisSerializer<>(Order.class));
        return adapter;
    }

    @Bean
    @Qualifier("order")
    public RedisTemplate<String, Order> redisTemplate2(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Order> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}

