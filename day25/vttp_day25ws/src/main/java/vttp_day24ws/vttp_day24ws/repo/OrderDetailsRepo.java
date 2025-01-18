package vttp_day24ws.vttp_day24ws.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailsRepo {
    @Autowired
    JdbcTemplate template;
}
