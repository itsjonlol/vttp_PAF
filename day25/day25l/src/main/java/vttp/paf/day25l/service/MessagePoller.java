package vttp.paf.day25l.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePoller {
    
    @Autowired
    @Qualifier("myredis")
    public RedisTemplate<String,String> template;

    @Async
    public void start() { //starts the worker threads. responsible for creating executor service and submit threadworker tasks to it
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new ThreadWorker(template,"task 01"));
        executorService.submit(new ThreadWorker(template,"task 02"));
    }
}
