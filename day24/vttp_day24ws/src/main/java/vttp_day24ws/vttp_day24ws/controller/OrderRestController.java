package vttp_day24ws.vttp_day24ws.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_day24ws.vttp_day24ws.model.Order;
import vttp_day24ws.vttp_day24ws.service.OrderService;


@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    OrderService orderService;

    // @PostMapping("/order")
    // public ResponseEntity<?> postOrder(@RequestBody Order order) {
    //     //TODO: process POST request
    //     //handling at controller level. still get the same message, but wont display the insertionerror timestamp format etc
    //     try {
    //         orderService.createOrderRecord(order);
    //         return ResponseEntity.status(200).header("Content-Type", "application/json").body(order);

    //     } catch (Exception ex) {
    //         Map<String,String> errorMessage = new HashMap<>();
    //             // but message is the same as below
    //         errorMessage.put("message", ex.getMessage());
    //         return ResponseEntity.status(400).header("Content-Type", "application/json").body(errorMessage);
    //     }
        
    // }
    //using custom exception
    @PostMapping("/order") //can stick to this custom exception style
    public ResponseEntity<?> postOrder2(@RequestBody Order order) {
            //all other exceptions are caught in the format also. not just your custom exceptions

            orderService.createOrderRecord(order);
            Map<String,String> message = new HashMap<>();
            message.put("message", "Order posted successfully");
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(message);

      

    }
    
    
}
