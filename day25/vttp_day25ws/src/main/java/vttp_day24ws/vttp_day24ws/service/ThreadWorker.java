package vttp_day24ws.vttp_day24ws.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ThreadWorker implements Runnable {

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${app.name}")  // Default topic if not provided
    private String appName;

    

    public ThreadWorker(RedisTemplate<String, String> template, String appName) {
        this.template = template;
        this.appName = appName;
    }

    @Override
    public void run() {
        // day 25 - slide 10
        ListOperations<String, String> listOps = template.opsForList();
        // String appName = appNameService.getAppName();
        

        while (true) {
            try {
                System.out.println("Queuing..." + appName);
                Optional<String> option = Optional.ofNullable(listOps.rightPop(appName, Duration.ofSeconds(100)));
                
                if (option.isEmpty()) {
                    continue;
                }
                String payload = option.get();
                System.out.println(payload);

                if (option.isPresent()) {
                    System.out.printf("*** NEW MESSAGE: %s\n", option.get());
                
        

                    System.out.printf("**** PUBLISHING TO TOPIC: %s\n", this.appName);
                    template.convertAndSend(appName,option.get() );
                }

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

}