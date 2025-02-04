package vttp_lecture22.vttp_lecture22.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp_lecture22.vttp_lecture22.model.Summary;
import static vttp_lecture22.vttp_lecture22.utils.SQLQueries.SQL_AUTHOR_SUMMARY;
import static vttp_lecture22.vttp_lecture22.utils.ToSummary.toSummary;

@Repository
public class KindleRepo {
    
    @Autowired
    JdbcTemplate template;

    public List<Summary> getSummaries(Integer bookCount, Integer limit) {
        SqlRowSet rs = template.queryForRowSet(SQL_AUTHOR_SUMMARY,bookCount,limit);
        List<Summary> summaries = new ArrayList<>();
        while (rs.next()) {
            Summary summary = toSummary(rs);
            summaries.add(summary);
            
        }
        return summaries;
    }
}
