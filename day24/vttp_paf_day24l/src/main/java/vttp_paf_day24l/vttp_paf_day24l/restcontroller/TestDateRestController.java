package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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
    public ResponseEntity<?> postMethodName(@RequestBody TestDate testDate) {
        //TODO: process POST request
        Map<String,String> message = new HashMap<>();
        message.put("message","approved");
        System.out.println(testDate.getName());
        System.out.println(testDate.getDate());
        System.out.println(testDate.getDateTime());
        System.out.println(testDate.getTimeStamp());
        testDateRepo.insertTestDate(testDate);
        
        
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(testDate);
    }
    
    @PostMapping("/testdate2")
    public ResponseEntity<?> postMethodName2(@RequestBody String testDateString) {
        //TODO: process POST request
        Map<String,String> message = new HashMap<>();
        message.put("message","approved");

        InputStream is = new ByteArrayInputStream(testDateString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject bodyJsonObject = reader.readObject();
        
       TestDate testDate = new TestDate();
       testDate.setName(bodyJsonObject.getString("name"));
       System.out.println(testDate.getName());
        
        
        
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(message);
    }


}
