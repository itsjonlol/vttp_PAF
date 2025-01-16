package vttp_day24ws.vttp_day24ws.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePoller {
    
    @Autowired
    @Qualifier("myredis")
    public RedisTemplate<String, String> template;

    @Autowired
    AppNameService appNameService;

    @Async
    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new ThreadWorker(template, appNameService.getAppName()));
        
    }
}
