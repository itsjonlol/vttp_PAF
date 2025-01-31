package vttp.paf.day27cnyconsumer.service;

import java.io.StringReader;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.paf.day27cnyconsumer.models.PurchaseOrder;
import vttp.paf.day27cnyconsumer.utils.OrderJsonFormatter;

//not used here
@Component
public class ThreadWorker{

    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> template;

    @Autowired
    PurchaseOrderService poSvc;

    // private String name;

    // public ThreadWorker(@Qualifier("myredis") RedisTemplate<String, String> template) {
    //     this.template = template;
      
    // }

    @Scheduled(fixedRate = 180000)
    public void processMessage() {
        // day 25 - slide 10
        ListOperations<String, String> listOps = template.opsForList(); //access to redis operations


        //continues to poll the redis queue for messages using rightpop. if cannot find within 30seconds, will retry
        //if find message, will print it and publish to topic.
        while (true) {
            try {
                System.out.println("Queuing...");
                Optional<String> option = Optional.ofNullable(listOps.leftPop("events", Duration.ofSeconds(30)));
                if (option.isEmpty()) {
                    break;
                }
                String payload = option.get();
                System.out.println(payload);

                if (option.isPresent()) {
                    System.out.printf("*** NEW MESSAGE: %s\n", option.get());
                    JsonReader reader = Json.createReader(new StringReader(option.get()));
                    JsonObject data = reader.readObject();
                    PurchaseOrder po = OrderJsonFormatter.JsonToPojo(data);
                    poSvc.insertPo(po);


                    

                    // template.convertAndSend("mytopic", this.name);
                }

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

}
