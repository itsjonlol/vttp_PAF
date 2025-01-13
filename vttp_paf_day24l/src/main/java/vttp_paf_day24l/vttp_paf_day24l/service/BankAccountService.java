package vttp_paf_day24l.vttp_paf_day24l.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp_paf_day24l.vttp_paf_day24l.model.BankAccount;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.AccountInactiveException;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.InsufficientBalanceException;
import vttp_paf_day24l.vttp_paf_day24l.repo.BankAccountRepo;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepo bankAccountRepo;

    public Boolean checkAccountExists(int accountId) {
        return bankAccountRepo.accountExists(accountId);
    }

    public BankAccount getAccountById(int accountId) {
        return bankAccountRepo.getAccountbyId(accountId);
    }
    @Transactional
    public Boolean transfer(int transfererAccountId, int transfereeAccountId,float transferAmount) {

        //retrieve both accouts
        BankAccount accountFrom = bankAccountRepo.getAccountbyId(transfererAccountId);
        BankAccount accountTo = bankAccountRepo.getAccountbyId(transfereeAccountId);

        Boolean isAccountFromActive = checkAccountActive(accountFrom);
        Boolean isAccountToActive = checkAccountActive(accountTo);
        Boolean isTrasnfererBalanceSufficient = checkSufficientBalance(accountFrom, transferAmount);

        if (isAccountFromActive && isAccountToActive && isTrasnfererBalanceSufficient) {
            accountFrom.setBalance(accountFrom.getBalance()-transferAmount);
            bankAccountRepo.updateAccountById(accountFrom);
            accountTo.setBalance(accountTo.getBalance()+transferAmount);
            bankAccountRepo.updateAccountById(accountTo);
            return true;
        }
        return false;// added transactional
        
        //check both account active or not
        //check transferrer has sufficient balance to transfer the transfer amount

    }

    private Boolean checkAccountActive(BankAccount account) {
        if (account.getIsActive().equals(true)) {
            return true;
        }
        throw new AccountInactiveException("ACCOUNT ID " + account.getId() + "-" + account.getFullName() +
        " is currently inactive");
    }
    private Boolean checkSufficientBalance(BankAccount account, float transferAmount) {
        Boolean isSufficientFund = (account.getBalance() - transferAmount) > 0;
        if (isSufficientFund) {
            return true;
        }
        throw new InsufficientBalanceException("Transferer " + account.getFullName() + " doesn't have enough funds for transfer");


    }
    
}
