package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.model.exception.ModifiedException;
import vttp_paf_day24l.vttp_paf_day24l.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    
    @Autowired
    AccountService acctSvc;

    // @PutMapping("/account/{acctId}")
    // public ResponseEntity<?> updateAccount (@PathVariable String acctId, @RequestBody String payload, @RequestHeader(HttpHeaders.IF_UNMODIFIED_SINCE) String date) throws ParseException{

    //     // parse the date from the header to a date object in GMT
    //     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    //     sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        
    //     Date unmodifiedDate = sdf.parse(date);
    //     sdf.format(unmodifiedDate);
    //     System.out.println("unmodified postman date " + unmodifiedDate);
    //     // System.out.println("Parsed Date in GMT: " + sdf.format(unmodifiedDate));
      
    //     Date modifiedDate = acctSvc.updateAccount(acctId, payload, unmodifiedDate);

    //     return ResponseEntity.ok()
    //                         .header(HttpHeaders.LAST_MODIFIED, sdf.format(modifiedDate))
    //                         .body("Account updated successfully.");

       

    // }
    @PutMapping("/account/{acctId}")
public ResponseEntity<?> updateAccount(@PathVariable String acctId, 
                                       @RequestBody String payload, 
                                       @RequestHeader(HttpHeaders.IF_UNMODIFIED_SINCE) String ifUnmodifiedSince) {
    try {
        // 1️⃣ Parse Postman's Header (GMT)
        System.out.println("no format postman: "+ ifUnmodifiedSince);
        SimpleDateFormat gmtFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT")); 
        Date unmodifiedSince = gmtFormat.parse(ifUnmodifiedSince);
        System.out.println("gmt formatted postman" + unmodifiedSince.toString());
        // System.out.println("Parsed If-Unmodified-Since from Postman: " + gmtFormat.format(unmodifiedSince));

        // 2️⃣ Process the update
        Date modifiedDate = acctSvc.updateAccount(acctId, payload, unmodifiedSince);

        // 3️⃣ Return formatted Last-Modified date in GMT
        return ResponseEntity.ok()
                             .header(HttpHeaders.LAST_MODIFIED, gmtFormat.format(modifiedDate))
                             .body("Account updated successfully.");
    } catch (ModifiedException e) {
        return ResponseEntity.status(412).body("Precondition Failed: Data has changed.");
    } catch (ParseException e) {
        return ResponseEntity.badRequest().body("Invalid Date Format.");
    }
}
}
