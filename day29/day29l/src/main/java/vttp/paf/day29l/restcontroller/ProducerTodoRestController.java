package vttp.paf.day29l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day29l.model.Todo;
import vttp.paf.day29l.service.ProducerTodoService;

@RestController
@RequestMapping("/api")
public class ProducerTodoRestController {
    
    @Autowired
    ProducerTodoService producerTodoService;
    
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody Todo todo) {
        //publish to subscribers
        producerTodoService.sendMessage(todo);

        //send to queue via leftpush
        producerTodoService.sendQueue(todo);
        
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(todo);
    }

    //simulate a resttemplate call. this is acting as a consumer
    @PostMapping("/messages/consumer")
    public ResponseEntity<?> postMethodName(@RequestBody Todo todo) {
        //TODO: process POST request
        System.out.println("Acting as a consumer:: " + todo.toString());
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(todo);
    }
    

}
