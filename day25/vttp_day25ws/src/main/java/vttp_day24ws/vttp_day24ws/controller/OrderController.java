package vttp_day24ws.vttp_day24ws.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp_day24ws.vttp_day24ws.model.Order;
import vttp_day24ws.vttp_day24ws.repo.RedisRepo;
import vttp_day24ws.vttp_day24ws.service.OrderService;



@Controller
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @Autowired
    RedisRepo redisRepo;

    // @Autowired
    // AppNameService appNameService;
    @Value("${app.name}")  // Default topic if not provided
    private String redisTopic;

    @GetMapping("/")
    public ModelAndView getForm() {
        ModelAndView mav = new ModelAndView("orderform");
        Order order = new Order();
        List<String> appList = redisRepo.getList("registrations");
        mav.addObject("applist",appList);
        mav.addObject("orderform",order);
        return mav;

    }
     
    @PostMapping("/order") // stick to try-catch for controller, as you can display the page instead of responseentity
    public ModelAndView postForm(@ModelAttribute Order order) {
        //TODO: process POST request
        System.out.println(order.toString());
        ModelAndView mav = new ModelAndView("postorderform");
        try {
            // orderService.createOrderRecord(order);
            redisRepo.pushToRepo(redisTopic,order);
            mav.setStatus(HttpStatus.OK);
        } catch (Exception ex) {
            mav.addObject("errorMessage",ex.getMessage());
            mav.setStatus(HttpStatusCode.valueOf(400));
        }
        
       
        return mav;
    }
    // @PostMapping("/order")
    // public ModelAndView postForm2(@ModelAttribute Order order) {
    //     //TODO: process POST request
        
    //     ModelAndView mav = new ModelAndView("postorderform");
    //     orderService.createOrderRecord(order);
         
       
       
    //     return mav;
    // }
}
