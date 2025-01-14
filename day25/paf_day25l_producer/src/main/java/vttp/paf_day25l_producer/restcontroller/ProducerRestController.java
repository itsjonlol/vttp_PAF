package vttp.paf_day25l_producer.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf_day25l_producer.model.Todo;
import vttp.paf_day25l_producer.service.ProducerService;



@RestController
@RequestMapping("/api")
public class ProducerRestController {
    @Autowired
    ProducerService producerService;

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody Todo todo) {
        producerService.sendMessage(todo);
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(todo);
    }
    
    @PostMapping("/publish")
    public ResponseEntity<?> sendOrder(@RequestBody String entity) {
        //TODO: process POST request
        // producerService.publish(order);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body("message sent");
    }
    

    
    
}
