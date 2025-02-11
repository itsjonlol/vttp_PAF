package vttp.paf.day29l.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.paf.day29l.bootstraps.MessageSubscriber;

@Configuration
public class RedisConfig {

   @Value("${spring.data.redis.host}")
   private String redisHost;

   @Value("${spring.data.redis.port}")
   private int redisPort;

   @Value("${spring.data.redis.database}")
   private int redisDatabase;

   //for pub/sub
   @Autowired
   private MessageSubscriber msgSubscriber;

   //standard redis
   @Bean("myredis")
   public RedisTemplate<String, String> createRedisTemplate() {
      final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
      config.setDatabase(redisDatabase);

      final JedisClientConfiguration jedisClient = JedisClientConfiguration
            .builder().build();
      final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
      jedisFac.afterPropertiesSet();

      final RedisTemplate<String, String> template = new RedisTemplate<>();
      template.setConnectionFactory(jedisFac);
      template.setKeySerializer(new StringRedisSerializer());
      template.setValueSerializer(new StringRedisSerializer());
      template.setHashKeySerializer(new StringRedisSerializer());
      template.setHashValueSerializer(new StringRedisSerializer());

      return template;
   }
   //for pubsub
   @Bean
   public RedisMessageListenerContainer createMessageSubscriber() {

      final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
      config.setDatabase(redisDatabase);

      final JedisClientConfiguration jedisClient = JedisClientConfiguration
            .builder().build();
      final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
      jedisFac.afterPropertiesSet();

      RedisMessageListenerContainer container = new RedisMessageListenerContainer();
      container.setConnectionFactory(jedisFac);
      container.addMessageListener(msgSubscriber, ChannelTopic.of("notifications"));

      return container;
   }
   
}
