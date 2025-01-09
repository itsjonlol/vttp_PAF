package vttp.day23ws.repo;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.day23ws.model.Details;
import static vttp.day23ws.repo.Queries.SQL_ORDER_ID_DETAILS;
import static vttp.day23ws.repo.Utils.toDetails;

@Repository
public class DetailsRepo {

    @Autowired
    JdbcTemplate template;

    public Optional<Details> getDetails(Integer orderId)  {
        SqlRowSet rs = template.queryForRowSet(SQL_ORDER_ID_DETAILS,orderId);

        if (!rs.next()) {
            return Optional.empty();
        }
        Details details = toDetails(rs);
        return Optional.of(details);

    }

    
}
