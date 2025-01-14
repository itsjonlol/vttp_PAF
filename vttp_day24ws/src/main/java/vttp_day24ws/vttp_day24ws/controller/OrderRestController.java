package vttp_day24ws.vttp_day24ws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_day24ws.vttp_day24ws.model.Orders;


@RestController
@RequestMapping("/api")
public class OrderRestController {

    @PostMapping("/order")
    public ResponseEntity<?> postOrder(@RequestBody Orders order) {
        //TODO: process POST request
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(order);
    }
    
    
}
