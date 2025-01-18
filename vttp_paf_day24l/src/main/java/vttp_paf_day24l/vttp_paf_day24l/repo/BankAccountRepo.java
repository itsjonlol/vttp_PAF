package vttp_paf_day24l.vttp_paf_day24l.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf_day24l.vttp_paf_day24l.model.BankAccount;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.AccountNotFoundException;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_SELECTBYBANKACCOUNTID;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_UPDATEBANKACCOUNTBYID;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Util.toBankAccount;


@Repository
public class BankAccountRepo {

    @Autowired
    JdbcTemplate template;
    
    public Boolean accountExists(int accountId) {
        //method #1 for checking
       try {
        BankAccount bankAccount = template.queryForObject(SQL_SELECTBYBANKACCOUNTID, 
        BeanPropertyRowMapper.newInstance(BankAccount.class),accountId);
        return true;
       } catch (DataAccessException error) {
        throw new AccountNotFoundException("The account you are looking for doesn't exist in the system.");
       }
      

       //method #2 for checking -> use count
       //method #3 -> use queryforrowset


    }


    public BankAccount getAccountbyId(int accountId) {
       
        SqlRowSet rs = template.queryForRowSet(SQL_SELECTBYBANKACCOUNTID,accountId);
        if (!rs.next()) {
            throw new AccountNotFoundException("Unfortunately, The account you are looking, " + accountId + "  for doesn't exist in the system.");
        }
        BankAccount account = toBankAccount(rs);
        return account;
       

    }

    public Boolean updateAccountById(BankAccount bankAccountToUpdate) {
        //to update in the service
        int accountUpdated = template.update(SQL_UPDATEBANKACCOUNTBYID,bankAccountToUpdate.getBalance(),
        bankAccountToUpdate.getId());
        
        return accountUpdated > 0;

    }

    


}
