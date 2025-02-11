package vttp.paf.day29l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.paf.day29l.model.Order;
import vttp.paf.day29l.service.ProducerOrderService;


@RestController
@RequestMapping("/api")
public class ProducerOrderRestController {
    @Autowired
    ProducerOrderService producerOrderService;
    
    @PostMapping("/orders")
    public ResponseEntity<?> sendOrder(@RequestBody Order order) {
        
        
        producerOrderService.publish(order);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(order);
    }
    
}
