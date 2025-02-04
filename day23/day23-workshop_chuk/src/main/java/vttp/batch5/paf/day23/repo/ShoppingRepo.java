package vttp.batch5.paf.day23.repo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day23.model.LineItem;
import vttp.batch5.paf.day23.model.PurchaseOrder;
import static vttp.batch5.paf.day23.repo.Queries.SQL_ADD_LINEITEMS;
import static vttp.batch5.paf.day23.repo.Queries.SQL_ADD_PURCHASEORDER;


@Repository
public class ShoppingRepo {
    
    @Autowired
    JdbcTemplate template;

    public Integer addPurchaseOrder(PurchaseOrder purchaseOrder) {

        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Date utilDate = purchaseOrder.getDeliveryDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        String formattedDate = sdf.format(utilDate);
        
        
    // Insert into purchaseorders and retrieve the generated purchase_id
    // int added = template.update(connection -> {
    //     PreparedStatement ps = connection.prepareStatement(SQL_ADD_PURCHASEORDER, new String[] { "purchase_id" });
    //     ps.setString(1, purchaseOrder.getName());
    //     ps.setString(2, purchaseOrder.getAddress());
    //     //if util date convert to string
    //     ps.setString(3,formattedDate);
    //     //if util date convert to sql date
    //     // ps.setDate(3, new java.sql.Date(purchaseOrder.getDeliveryDate().getTime()));
    //     return ps;
    // }, keyHolder);

    PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // Create a PreparedStatement with the specified SQL and return keys
                PreparedStatement ps = con.prepareStatement(SQL_ADD_PURCHASEORDER, new String[]{"purchase_id"});
                ps.setString(1, purchaseOrder.getName());
                ps.setString(2, purchaseOrder.getAddress());
                //if util date convert to string
                ps.setString(3,formattedDate);
                //if util date convert to sql date
                // ps.setDate(3, new java.sql.Date(purchaseOrder.getDeliveryDate().getTime()));
                return ps;
            }
        };
    template.update(psc,keyHolder);
    
    // Get the generated purchase_id
    Number generatedId = keyHolder.getKey();
    if (generatedId == null) {
        throw new IllegalStateException("Failed to insert");
    }
    int purchaseId = generatedId.intValue();

        // int added = template.update(SQL_ADD_PURCHASEORDER,purchaseOrder.getUsername(),purchaseOrder.getAddress(),
        // purchaseOrder.getDeliveryDate());
        
        return purchaseId;

    
    }

    public Boolean insertLineItems(PurchaseOrder purchaseOrder,Integer purchaseId) {
        List<LineItem> lineItems = purchaseOrder.getLineItems();
        List<Object[]> params = lineItems.stream().map(item -> new Object[]{
            item.getName(),item.getQuantity(),item.getQuantity(),purchaseId
        } ).collect(Collectors.toList());

        int[] iUpdated = template.batchUpdate(SQL_ADD_LINEITEMS,params);
        
        return (Arrays.stream(iUpdated).allMatch(updatedRows -> updatedRows > 0));

    }
    

    


}
