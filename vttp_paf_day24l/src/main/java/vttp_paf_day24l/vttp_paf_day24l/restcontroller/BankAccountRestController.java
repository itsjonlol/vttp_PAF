package vttp_paf_day24l.vttp_paf_day24l.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp_paf_day24l.vttp_paf_day24l.model.BankAccount;
import vttp_paf_day24l.vttp_paf_day24l.service.BankAccountService;

@RestController
@RequestMapping("/api")
public class BankAccountRestController {

    @Autowired
    BankAccountService bankAccountService;

    @GetMapping("/bankaccounts/exists/{account-id}")
    public ResponseEntity<?> checkAccountExists(@PathVariable("account-id") Integer accountId) {
        Boolean isAccountExists = bankAccountService.checkAccountExists(accountId);
        // if (!isAccountExists) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").body(isAccountExists);
        // }
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(isAccountExists);
    }

    @GetMapping("/bankaccounts/{account-id}") 
    public ResponseEntity<?> getAccountById(@PathVariable("account-id") Integer accountId) {
        BankAccount bankAccount = bankAccountService.getAccountById(accountId);
        return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").body(bankAccount);
    }

    @PostMapping("/bankaccounts/transfer/from/{account-from}/to/{account-to}/amount/{transfer-amount}")
    public ResponseEntity<?> transferFund(@PathVariable("account-from") Integer accountFromId,
    @PathVariable("account-to") Integer accountToId, @PathVariable("transfer-amount") Float amount) {
        //TODO: process POST request
        Boolean bTransferred = bankAccountService.transfer(accountToId, accountToId,amount);
        return ResponseEntity.ok().body(bTransferred);
    }
    

    
}
