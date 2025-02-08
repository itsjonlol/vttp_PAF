package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.model.TestDate;
import vttp_paf_day24l.vttp_paf_day24l.repo.TestDateRepo;


@RestController
@RequestMapping("/api")
public class TestDateRestController {
    @Autowired
    TestDateRepo testDateRepo;

    @GetMapping("/testdate")
    public ResponseEntity<?> getTestDate() {
        TestDate testDate = testDateRepo.testDate();
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(testDate);
        
    }
    @PostMapping("/testdate")
    public ResponseEntity<?> postMethodName() {
        //TODO: process POST request
        Map<String,String> message = new HashMap<>();
        message.put("message","approved");
        
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(message);
    }
    


}
