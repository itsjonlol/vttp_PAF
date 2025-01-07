package main.java.vttp_lecture22ws.vttp_lecture22ws.restcontroller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import main.java.vttp_lecture22ws.vttp_lecture22ws.model.RSVP;
import main.java.vttp_lecture22ws.vttp_lecture22ws.service.RSVPService;




@RestController
@RequestMapping("/api")
public class RSVPRestController {
    
    @Autowired
    RSVPService rsvpService;

    @GetMapping("/rsvps")
    public ResponseEntity<?> getAllRsvp() {
        List<RSVP> results = rsvpService.getAllRSVP();
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(results);
    }
    
    @GetMapping("/{rsvpemail}")
    public ResponseEntity<?> getRSVPByEmail(@PathVariable("rsvpemail") String email) {
        Optional<RSVP> optRSVP = rsvpService.getRSVPByEmail(email);
        if (optRSVP.isEmpty()) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("Error Message","No RSVP found");
            return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);
        }
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(optRSVP.get());
    }

    @PostMapping("/rsvp")
    public ResponseEntity<?> addRSVP(@RequestBody String rsvpString) throws ParseException {
        InputStream is = new ByteArrayInputStream(rsvpString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject rsvpJsonObject = reader.readObject();
        String requestedEmail = rsvpJsonObject.getString("email");
        

        RSVP rsvp = new RSVP();
        // String finalEmail = rsvpJsonObject.getString("email");
        rsvp.setEmail(requestedEmail);
        rsvp.setPhone(rsvpJsonObject.getString("phone"));
        rsvp.setComments(rsvpJsonObject.getString("comments"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date rsvpDate = sdf.parse(rsvpJsonObject.getString("confirmDate"));
        rsvp.setConfirmDate(rsvpDate);
  

        Optional<RSVP> optRSVP = rsvpService.getRSVPByEmail(requestedEmail);
        if (!optRSVP.isEmpty()) {
            rsvpService.updateRSVP(rsvp, requestedEmail);
        } else {
            rsvpService.addRSVP(rsvp);
        }
        
        // RSVP rsvp = new RSVP();
        // rsvp.setEmail(rsvpJsonObject.getString("email"));
        // rsvp.setPhone(rsvpJsonObject.getString("phone"));
        // rsvp.setComments(rsvpJsonObject.getString("comments"));
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date rsvpDate = sdf.parse(rsvpJsonObject.getString("confirmDate"));
        // rsvp.setConfirmDate(rsvpDate);



        return ResponseEntity.status(HttpStatus.CREATED).body("Operation successful");
    }
    
    @PutMapping("/rsvp/{rsvpemail}") 
    public ResponseEntity<?> updateRSVP(@PathVariable("rsvpemail") String requestedEmail,
    @RequestBody String rsvpString) throws ParseException {
        Optional<RSVP> optRSVP = rsvpService.getRSVPByEmail(requestedEmail);
        if (optRSVP.isEmpty()) {
            Map<String,String> errorMessage = new HashMap<>();
            errorMessage.put("Error Message","No RSVP found");
            return ResponseEntity.status(404).header("Content-Type", "application/json").body(errorMessage);
        }

        InputStream is = new ByteArrayInputStream(rsvpString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject rsvpJsonObject = reader.readObject();

        RSVP rsvp = new RSVP();
        String finalEmail = rsvpJsonObject.getString("email");
        rsvp.setEmail(finalEmail);
        rsvp.setPhone(rsvpJsonObject.getString("phone"));
        rsvp.setComments(rsvpJsonObject.getString("comments"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date rsvpDate = sdf.parse(rsvpJsonObject.getString("confirmDate"));
        rsvp.setConfirmDate(rsvpDate);
        rsvpService.updateRSVP(rsvp, finalEmail);
        return ResponseEntity.status(201).body("Update successful");
    }
        
}
    

