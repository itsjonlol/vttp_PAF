package vttp_paf_day24l.vttp_paf_day24l.service;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.ModifiedException;
import vttp_paf_day24l.vttp_paf_day24l.repo.AccountRepo;

@Service
public class AccountService {
    
    @Autowired
    AccountRepo accountRepo;

    // @Transactional
    // public Date updateAccount(String acctId, String payload, Date unmodifiedDate){
    //     //parse the payload to extract the balance
    //     SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    //     SimpleDateFormat sdf2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    //     double balance = parsePayload(payload);
    //     sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    //     String formattedUnmodifiedDate = sdf.format(unmodifiedDate);
    //     System.out.println("gmt formatted unmodifeid date is " + formattedUnmodifiedDate);

    //     //Retreive the account's last modification date
    //     Date lastModifiedDate = accountRepo.getLastModifiedDate2(acctId);
    //     String formattedLastModifiedDate = sdf.format(lastModifiedDate);
    //     System.out.println("last modified date no sdf from sql: "+ lastModifiedDate);
    //     System.out.println("last modified date from sql " + formattedLastModifiedDate);

    //     if(!formattedUnmodifiedDate.equals(formattedLastModifiedDate)){
    //         throw new ModifiedException("The account was modified after the provided date");

    //     }

    //     accountRepo.updateBalance(acctId, balance);

    //     return accountRepo.getLastModifiedDate(acctId);

    // }
    @Transactional
    public Date updateAccount(String acctId, String payload, Date unmodifiedSince) {
    // 1️⃣ Retrieve `last_update` from MySQL as `Date`
    Date lastUpdate = accountRepo.getLastModifiedDate2(acctId);
    System.out.println("Raw last_update from MySQL: " + lastUpdate);

    // 2️⃣ Convert both dates to GMT format for comparison
    SimpleDateFormat gmtFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
    
    String formattedLastUpdate = gmtFormat.format(lastUpdate);
    // SimpleDateFormat gmtFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
    

    
    String formattedUnmodifiedSince = gmtFormat.format(unmodifiedSince);
    // gmtFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

    System.out.println("Formatted last_update (GMT): " + formattedLastUpdate);
    System.out.println("Formatted If-Unmodified-Since (GMT): " + formattedUnmodifiedSince);

    // 3️⃣ Compare as Strings (since they are now in the same format)
    if (!formattedLastUpdate.equals(formattedUnmodifiedSince)) {
        throw new ModifiedException("The account was modified after the provided date.");
    }

    // 4️⃣ Parse payload and update balance
    double balance = parsePayload(payload);
    accountRepo.updateBalance(acctId, balance);

    // 5️⃣ Retrieve updated `last_update` timestamp
    return accountRepo.getLastModifiedDate2(acctId);
}
    
    public Double parsePayload(String payload){

        JsonObject jObject = Json.createReader(new StringReader(payload)).readObject();
        return jObject.getJsonNumber("amount").doubleValue();
    }

    @Transactional
    public void transfer(String fromAcct, String toAcct, String payload) {

        double amount = parsePayload(payload);
        final Optional<Double> optFrom = accountRepo.getBalance(fromAcct);
        final Optional<Double> optTo = accountRepo.getBalance(toAcct);
        if(optFrom.isEmpty() || optTo.isEmpty() || (optFrom.get() < amount)){
            throw new IllegalArgumentException("Incorrect parameters");
        }
        accountRepo.withdraw(fromAcct, amount);
        accountRepo.deposit(toAcct,amount);

        if (!(accountRepo.withdraw(fromAcct, amount) || accountRepo.deposit(toAcct,amount))){
            throw new CannotCreateTransactionException("Cannot perform transfer");

        }
        
    }
}
