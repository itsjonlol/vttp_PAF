package vttp.batch5.paf.day23.repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ShoppingRepo {
    
    @Autowired
    JdbcTemplate template;

    // public List<RSVP> getAllRSVP() {
    //    List<RSVP> results = new ArrayList<>();
    //    SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_RSVP);
    //    while (rs.next()) {
    //     results.add(RSVP.toRSVP(rs));
    // }

    //    return results;
    // }

    // public Optional<RSVP> getRSVPByEmail(String email) {
    //     SqlRowSet rs = template.queryForRowSet(SQL_RSVP_BY_EMAIL,email);
    //     if (!rs.next()) {
    //         return Optional.empty();
    //     }
    //     RSVP rsvp = RSVP.toRSVP(rs);
    //     return Optional.of(rsvp);
    // }

    // public Boolean addRSVP(RSVP rsvp) {

    //     int added = template.update(SQL_ADD_RSVP,rsvp.getEmail(),rsvp.getPhone(),rsvp.getConfirmDate(),rsvp.getComments());
    //     return added>0;
    // }
    
    // public Boolean updateRSVP(RSVP rsvp,String email) {
    //     int updated = template.update(SQL_UPDATE_RSVP,rsvp.getEmail(),rsvp.getPhone(),rsvp.getConfirmDate(),rsvp.getComments(),email);
    //     return updated>0;
    // }

    // public Integer getRSVPCount() {
    //     return null;
    // }

    


}
