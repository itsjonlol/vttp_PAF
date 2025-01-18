package vttp_paf_day24l.vttp_paf_day24l.utils;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import vttp_paf_day24l.vttp_paf_day24l.model.BankAccount;

public class Util {


     public static BankAccount toBankAccount(SqlRowSet rs) {
        BankAccount bankAccount = new BankAccount();

        bankAccount.setId(rs.getInt("id"));
        bankAccount.setFullName(rs.getString("fullName"));
        bankAccount.setIsActive(rs.getBoolean("isActive"));
        bankAccount.setBalance(rs.getFloat("balance"));
        
        return bankAccount;
    }
}
