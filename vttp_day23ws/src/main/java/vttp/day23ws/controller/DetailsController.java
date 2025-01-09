package vttp.day23ws.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vttp.day23ws.model.Details;
import vttp.day23ws.service.DetailsService;



@Controller
public class DetailsController {
    
    @Autowired
    DetailsService detailsService;

    @GetMapping("api/order/total/{order_id}")
    @ResponseBody
    public ResponseEntity<?> getOrderDetailsREST(@PathVariable("order_id") Integer orderId) {
        Optional<Details> optDetails = detailsService.getDetails(orderId);
        if (optDetails.isEmpty()) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("Error Message","No Details found");
            return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);
        }
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(optDetails.get());
    
    }

    @GetMapping("/order/total/{order_id}")
    public ModelAndView getOrderDetails(@PathVariable("order_id") Integer orderId) {
        ModelAndView mav = new ModelAndView("tracker");
        Optional<Details> optDetails = detailsService.getDetails(orderId);
        if (optDetails.isEmpty()) {
            mav.addObject("errorMessage", "No Details found");
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }
    
        // Add the details to the model to be used in the Thymeleaf template
        mav.addObject("orderDetails", optDetails.get());
        mav.setStatus(HttpStatusCode.valueOf(200));
        return mav; // The Thymeleaf template that will display the order details
    }

    @PostMapping("/details")
    public ModelAndView postForm(@RequestParam("orderId") Integer orderId) {
        //TODO: process POST request
        // can use MVM also
        // Integer orderId = Integer.valueOf(form.getFirst("orderId"));
        ModelAndView mav = new ModelAndView("tracker");
        Optional<Details> optDetails = detailsService.getDetails(orderId);
        if (optDetails.isEmpty()) {
            mav.addObject("errorMessage", "No Details found");
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }
    
        // Add the details to the model to be used in the Thymeleaf template
        mav.addObject("orderDetails", optDetails.get());
        mav.setStatus(HttpStatusCode.valueOf(200));
        return mav; // The Thymeleaf template that will display the order details

    }
    
    
}
