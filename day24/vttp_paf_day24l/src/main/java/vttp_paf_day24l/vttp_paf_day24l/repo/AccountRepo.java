package vttp_paf_day24l.vttp_paf_day24l.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf_day24l.vttp_paf_day24l.model.exception.AccountNotFoundException;
import vttp_paf_day24l.vttp_paf_day24l.utils.Queries;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_GET_LAST_MODIFIED;
@Repository
public class AccountRepo {
    
    @Autowired
    JdbcTemplate template;

    public Date getLastModifiedDate(String acctId){
        return template.queryForObject(Queries.SQL_GET_LAST_MODIFIED,Date.class,acctId);
    }
    public Date getLastModifiedDate2(String acctId) {
        
        SqlRowSet rs = template.queryForRowSet(SQL_GET_LAST_MODIFIED,acctId);
        if (!rs.next()) {
            throw new AccountNotFoundException("Unfortunately, The account you are looking, " + acctId + "  for doesn't exist in the system.");
        }
        
        String timestamp = rs.getString("last_update");
        System.out.println("string timestamp from sql" + timestamp);
        // SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        
        Date date;
        try {
        
        date =  sdf.parse(timestamp);
        // date = rs.getDate("last_update");
        // Timestamp lastUpdate = rs.getTimestamp("last_update");
        // date= new Date(lastUpdate.getTime());
        
        } catch (ParseException e) {
        date = new Date();
        }
        return date;
        
    }
    public int updateBalance(String acctId, double amt){
        int updated = template.update(Queries.SQL_UPDATE_ACCOUNT,amt,acctId);
        return updated;
    }

    public boolean withdraw(String acctId, double amount) {
        final int rowCount = template.update(Queries.SQL_WITHDRAW, amount, acctId );
        return rowCount>0;
    }

    public boolean deposit(String acctId, double amount) {
        final int rowCount = template.update(Queries.SQL_DEPOSIT, amount, acctId);
        return rowCount>0;
    }

    public Optional<Double> getBalance (String acctId){
        final SqlRowSet rs = template.queryForRowSet(Queries.SQL_GET_BALANCE,acctId);
        if (rs.next()) {
            double balance = rs.getDouble("balance");
            // Check if the value was NULL
            if (rs.wasNull()) {
                return Optional.empty();
            }
            return Optional.of(balance);
        }
        return Optional.empty();
    }
    
}
