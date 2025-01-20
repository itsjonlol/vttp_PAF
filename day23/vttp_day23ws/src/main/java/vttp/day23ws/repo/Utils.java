package vttp.day23ws.repo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import vttp.day23ws.model.Details;

public class Utils {
    public static Details toDetails(SqlRowSet rs)   {
        Details details = new Details();
        details.setOrderId(rs.getInt("order_id"));
        // Timestamp orderTimestamp = rs.getTimestamp("order_date");
        String orderDateString = rs.getString("order_date");

        // Date orderDate = new Date(orderTimestamp.getTime());
        // LocalDateTime orderDateTime = rs.getObject("order_date", LocalDateTime.class);

        // LocalDate orderLocalDate = rs.getTimestamp("order_date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            java.util.Date orderDate = sdf.parse(orderDateString);
            details.setOrderDate(new Date(orderDate.getTime()));
            // Date orderDate = sdf.parse(orderDateString);
            // details.setOrderDate(orderDate);

        } catch (ParseException ex) {
           System.out.println(ex.getMessage());
        }
        
        
        // details.setOrderDate(rs.getDate("order_date"));
        details.setCustomerid(rs.getInt("customer_id"));
        details.setTotalFinalPrice(rs.getFloat("total_final_price"));
        details.setTotalCostPrice(rs.getFloat("total_cost_price"));
        return details;
    }
}
