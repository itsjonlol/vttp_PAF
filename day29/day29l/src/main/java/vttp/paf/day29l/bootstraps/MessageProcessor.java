package vttp.paf.day29l.bootstraps;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Component
public class MessageProcessor {

   @Autowired @Qualifier("myredis")
   private RedisTemplate<String, String> template;

   RestTemplate restTemplate = new RestTemplate();
   @Async
   public void startPoller() {

      // Create a polling thread
      final Runnable poller = () -> {

         final ListOperations<String, String> queue = template.opsForList();
         while (true) {
            Optional<String> opt = Optional.ofNullable(
                //to simulate -> need to lpush messages <"message">
               queue.rightPop("messages", Duration.ofSeconds(5)) // brpop
            );
            if (opt.isEmpty())
               continue;
            
            String data = opt.get();
            System.out.printf(">>>> DEQUEUED from publisher: [%s] %s\n", new Date(), data);
            if (opt.isPresent()) {
                //if just want to send to subscibed channel

                // template.convertAndSend("notifications", data);

             InputStream is = new ByteArrayInputStream(data.getBytes());
             JsonReader reader = Json.createReader(is);
        JsonObject toDoJson = reader.readObject();
    //   Todo todo = TodoJsonFormatter.JsonToPojo(toDoJson);

       String url = "http://localhost:4000/api/messages/consumer";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));



        //OR CAN orderData directly
        try {
            System.out.println("doing a post api call from processor");
            RequestEntity<String> requestEntity = RequestEntity.post(url)
                                                           .headers(headers)
                                                           .body(toDoJson.toString());
            ResponseEntity<String> responseResult = restTemplate.exchange(requestEntity,String.class);
            System.out.println("Result from api call" + responseResult.getBody());
            
        } catch (RestClientException ex) {
            System.out.println(ex.getMessage());
        }

            }
            
    
         }

      };

      // Submit messge poller to thread
      Executors.newSingleThreadExecutor().execute(poller);
   }
}