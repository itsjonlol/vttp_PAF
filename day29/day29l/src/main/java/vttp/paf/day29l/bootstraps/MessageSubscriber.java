package vttp.paf.day29l.bootstraps;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

//by right should be at the consumer side
@Component
public class MessageSubscriber implements MessageListener {
    RestTemplate restTemplate = new RestTemplate();

   @Override
   public void onMessage(Message msg, byte[] pattern) {

      String pat = new String(pattern);
      String body = new String(msg.getBody());

      System.out.printf(">>>> MESSAGE(subscriber): %s %s %s\n",
            new Date(), pat, body);

      
      InputStream is = new ByteArrayInputStream(body.getBytes());
      JsonReader reader = Json.createReader(is);
      JsonObject jsonObject = reader.readObject();
      

        String url = "http://localhost:4000/api/order";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        try {
            System.out.println("doing a post api call from subscriber");
            RequestEntity<String> requestEntity = RequestEntity.post(url)
                                                           .headers(headers)
                                                           .body(jsonObject.toString());
            ResponseEntity<String> responseResult = restTemplate.exchange(requestEntity,String.class);
            System.out.println("Result from api call" + responseResult.getBody());
            
        } catch (RestClientException ex) {
            
            System.out.println(ex.getMessage());
        }  

    //for todo
    //    String url = "http://localhost:4000/api/messages/consumer";
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //     headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));



    //     //OR CAN orderData directly
    //     // try {
    //     //     System.out.println("doing a post api call from subscriber");
    //     //     RequestEntity<String> requestEntity = RequestEntity.post(url)
    //     //                                                    .headers(headers)
    //     //                                                    .body(toDoJson.toString());
    //     //     ResponseEntity<String> responseResult = restTemplate.exchange(requestEntity,String.class);
    //     //     System.out.println("Result from api call" + responseResult.getBody());
            
    //     // } catch (RestClientException ex) {
    //     //     System.out.println(ex.getMessage());
    //     // }
    
      
    


      
   }

}
