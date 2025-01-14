package vttp_day24ws.vttp_day24ws.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import vttp_day24ws.vttp_day24ws.model.Orders;
import vttp_day24ws.vttp_day24ws.service.OrderService;



@Controller
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public ModelAndView getForm() {
        ModelAndView mav = new ModelAndView("orderform");
        Orders order = new Orders();
        mav.addObject("orderform",order);
        return mav;

    }
    
    @PostMapping("/order")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
}
