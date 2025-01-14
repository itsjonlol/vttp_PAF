package vttp.paf.day25l.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp.paf.day25l.service.SubscriberService;

@Configuration
public class RedisConfig {
    
   

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Autowired
    SubscriberService subscriberService;

    public RedisConnectionFactory createConnectionFactory() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost,redisPort);
        config.setDatabase(0);

        if (!redisUsername.equals("") && redisPassword.equals("")) {
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
            
        }
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(config,jedisClient);
        jedisConnectionFactory.afterPropertiesSet();

        return jedisConnectionFactory;

    }

    @Bean("myredis")
    public RedisTemplate<String, String> redisObjectTemplate01() {
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public RedisMessageListenerContainer createMessageListenerContainer() {
        RedisConnectionFactory redisConnectionFactory = createConnectionFactory();
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(subscriberService, ChannelTopic.of("mytopic"));
        return container;
    }


    // @Bean
    // public JedisConnectionFactory jedisConnectionFactory() {
    //     RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
    //     rsc.setHostName(redisHost);
    //     rsc.setPort(redisPort);

    //     if(redisUsername.trim().length() > 0) {
    //         rsc.setUsername(redisUsername);
    //         rsc.setPassword(redisPassword);
    //     }

    //     JedisClientConfiguration jcc = JedisClientConfiguration.builder().build();
    //     JedisConnectionFactory jcf = new JedisConnectionFactory(rsc, jcc);
    //     jcf.afterPropertiesSet();

    //     return jcf;
    // }

    // @Bean(ConstantVar.template01)
    // public RedisTemplate<String, String> redisObjectTemplate01() {
    //     RedisTemplate<String, String> template = new RedisTemplate<>();
    //     template.setConnectionFactory(jedisConnectionFactory());
    //     template.setKeySerializer(new StringRedisSerializer());
    //     template.setValueSerializer(new StringRedisSerializer());
    //     template.setHashKeySerializer(new StringRedisSerializer());
    //     template.setHashValueSerializer(new StringRedisSerializer());
    //     return template;
    // }
}
