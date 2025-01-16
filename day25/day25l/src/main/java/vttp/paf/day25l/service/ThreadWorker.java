package vttp.paf.day25l.service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class ThreadWorker implements Runnable {

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    private String name;

    public ThreadWorker(RedisTemplate<String, String> template, String name) {
        this.template = template;
        this.name = name;
    }

    @Override
    public void run() {
        // day 25 - slide 10
        ListOperations<String, String> listOps = template.opsForList(); //access to redis operations

        template.convertAndSend("mytopic", this.name); //publishes a message to redis topic

        //continues to poll the redis queue for messages using rightpop. if cannot find within 30seconds, will retry
        //if find message, will print it and publish to topic.
        while (true) {
            try {
                System.out.println("Queuing...");
                Optional<String> option = Optional.ofNullable(listOps.rightPop("myqueue", Duration.ofSeconds(30)));
                if (option.isEmpty()) {
                    continue;
                }
                String payload = option.get();
                System.out.println(payload);

                if (option.isPresent()) {
                    System.out.printf("*** NEW MESSAGE: %s\n", option.get());
                    // JsonReader reader = Json.createReader(new StringReader(option.get()));
                    // JsonObject data = reader.readObject();

                    System.out.printf("**** PUBLISHING: %s\n", this.name);
                    template.convertAndSend("mytopic", this.name);
                }

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

}