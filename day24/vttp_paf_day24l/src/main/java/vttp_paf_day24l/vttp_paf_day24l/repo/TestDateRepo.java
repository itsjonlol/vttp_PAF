package vttp_paf_day24l.vttp_paf_day24l.repo;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_paf_day24l.vttp_paf_day24l.model.TestDate;
import vttp_paf_day24l.vttp_paf_day24l.model.exception.AccountNotFoundException;
@Repository
public class TestDateRepo {
    
    @Autowired
    JdbcTemplate template;

    public static final String SQL_TESTDATE = "select * from example_table where name = ?";

    public TestDate testDate() {
        SqlRowSet rs = template.queryForRowSet(SQL_TESTDATE,"lol");
        if (!rs.next()) {
            throw new AccountNotFoundException("The account you are looking for doesn't exist in the system.");
        }
        TestDate testDate = new TestDate();
        testDate.setId(rs.getInt("id"));
        testDate.setName(rs.getString("name"));
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat sdfTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        // sdfDateTime.setTimeZone(TimeZone.getTimeZone("GMT")); 
        sdfTimeStamp.setTimeZone(TimeZone.getTimeZone("GMT"));

        
        System.out.println("datetime column is " + rs.getString("datetime_column"));
        System.out.println("timestamp column is " + rs.getString("timestamp_column"));

        testDate.setDate(rs.getDate("date_column"));
        try {
            testDate.setDateTime(sdfDateTime.parse(rs.getString("datetime_column")));
            testDate.setTimeStamp(sdfTimeStamp.parse(rs.getString("timestamp_column")));
        } catch (ParseException e) {
           System.out.println("failed to parse");
            testDate.setDateTime(new Date());
            testDate.setTimeStamp(new Date());
        }
        

    
        return testDate;
    }
    public void insertTestDate(TestDate testDate) {
        testDate.setName("testing dates");
        testDate.setDate(new Date());
        testDate.setDateTime(new Date());
        testDate.setTimeStamp(new Date());
        
    }

}
