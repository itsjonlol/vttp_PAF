package vttp_day24ws.vttp_day24ws.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp_day24ws.vttp_day24ws.model.Order;
import vttp_day24ws.vttp_day24ws.model.OrderDetails;
import vttp_day24ws.vttp_day24ws.model.exceptions.InsertionException;
import static vttp_day24ws.vttp_day24ws.utils.Queries.SQL_CREATE_ORDER;
import static vttp_day24ws.vttp_day24ws.utils.Queries.SQL_CREATE_ORDERDETAILS;

@Repository
public class OrderRepo {
    
    @Autowired
    JdbcTemplate template;

    public int createOrder(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc;
        psc = new PreparedStatementCreator() {
            
            
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE_ORDER, new String[] {"order_id"});
                ps.setDate(1,order.getOrderDate());
                ps.setString(2, order.getCustomerName());
                ps.setString(3,order.getShipAddress());
                ps.setString(4, order.getNotes());
                ps.setFloat(5, order.getTax());

                return ps;
            }
            
        };

        int affectedrows = template.update(psc, keyHolder);
        //not sure if necessary, because it is not getting hit
        if (affectedrows == 0) {
            throw new InsertionException("Error inserting records at psc level");
        }
        int createdOrderId = keyHolder.getKey().intValue();
        return createdOrderId;
        
    }

    public Boolean createOrderDetails(Order order,Integer iOrderId) {
        List<OrderDetails> orderDetailsList = order.getOrderDetailsList();
        

        List<Object[]> params = orderDetailsList.stream().map(orderDetails -> new Object[]{
            orderDetails.getProduct(),orderDetails.getUnitPrice(),orderDetails.getDiscount(),
            orderDetails.getQuantity(),iOrderId
        } ).collect(Collectors.toList());
        
        int[] iUpdated = template.batchUpdate(SQL_CREATE_ORDERDETAILS,params);
       
        
        if (Arrays.stream(iUpdated).allMatch(updatedRows -> updatedRows > 0)) {
            return true;
        } else {
            throw new InsertionException("Error inserting records details");
        }
        
    }

}
