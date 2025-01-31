package vttp.paf.day27cnyconsumer.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

@Component
public class MessagePoller {
    
    @Autowired
    @Qualifier("myredis")
    public RedisTemplate<String,String> template;

    @Autowired
    private ThreadWorker threadWorker;

    @Autowired
    PurchaseOrderService poSvc;

    // @Async
    // public void start() { //starts the worker threads. responsible for creating executor service and submit threadworker tasks to it
    //     // ExecutorService executorService = Executors.newFixedThreadPool(1);
    //     // executorService.submit(threadWorker);
    //     ListOperations<String, String> listOps = template.opsForList();
    //     while (true) {
    //         try {
    //             System.out.println("Queuing...");
    //             Optional<String> option = Optional.ofNullable(listOps.leftPop("events", Duration.ofSeconds(30)));
    //             if (option.isEmpty()) {
    //                 break;
    //             }
    //             String payload = option.get();
    //             System.out.println(payload);

    //             if (option.isPresent()) {
    //                 System.out.printf("*** NEW MESSAGE: %s\n", option.get());
    //                 JsonReader reader = Json.createReader(new StringReader(option.get()));
    //                 JsonObject data = reader.readObject();
    //                 PurchaseOrder po = OrderJsonFormatter.JsonToPojo(data);
    //                 poSvc.insertPo(po);


                    

    //                 // template.convertAndSend("mytopic", this.name);
    //             }

    //         } catch (Exception ex) {
    //             System.err.println(ex.getMessage());
    //         }
    //     }

    // }
    @Scheduled(fixedRate = 30000) // Poll every 5 minutes (300,000 ms)
    public void start() {
        
        ListOperations<String, String> listOps = template.opsForList();
        System.out.println("Checking Redis queue for messages...");

        List<String> messages = new ArrayList<>();
        String payload;

        // Fetch multiple messages in one go
        while ((payload = listOps.leftPop("events")) != null) {
            System.out.println("data found");
            messages.add(payload);
        }

        if (messages.isEmpty()) {
            System.out.println("No new messages found.");
           
            return; // Exit early if no messages are found
        }

        System.out.println("Processing " + messages.size() + " messages...");
        for (String message : messages) {
            processPayload(message);
        }
        
        
    }

    private void processPayload(String payload) {
        try {
            System.out.printf("*** NEW MESSAGE: %s\n", payload);
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject data = reader.readObject();
            PurchaseOrder po = OrderJsonFormatter.JsonToPojo(data);
            poSvc.insertPo(po);
        } catch (Exception ex) {
            System.err.println("Error processing message: " + ex.getMessage());
        }
    }
        
    
}