package vttp_paf_day24l.vttp_paf_day24l.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp_paf_day24l.vttp_paf_day24l.model.Reservation;
import static vttp_paf_day24l.vttp_paf_day24l.utils.Queries.SQL_INSERT_RESERVATION;
@Repository
public class ReservationRepo {

    @Autowired
    JdbcTemplate template;

    public int createReservation(Reservation reservation) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // Create a PreparedStatement with the specified SQL and return keys
                PreparedStatement ps = con.prepareStatement(SQL_INSERT_RESERVATION, new String[]{"id"});
                ps.setString(1, reservation.getFullName());
                ps.setDate(2, reservation.getReservationDate());
                return ps;
            }
        };
        
        
        
        

        template.update(psc,keyHolder);
        int iReservationId = keyHolder.getKey().intValue();
        return iReservationId;
    }

    // public Boolean createReservationDetails(ReservationDetail reservationDetail) {
    //     int iPdated = template.update
    // }
}   
