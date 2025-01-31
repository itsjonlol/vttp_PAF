package vttp.paf.day27cnyconsumer.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;



@Service
public class SubscriberService implements MessageListener {

    
    //purely for testing
    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        String orderData = new String(message.getBody());
        
        System.out.println("From PUBSUB TESTING: " + orderData);
    }

    
}
