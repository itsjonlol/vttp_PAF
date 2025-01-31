package vttp.paf.day27cnyconsumer.repo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.paf.day27cnyconsumer.models.LineItem;
import vttp.paf.day27cnyconsumer.models.PurchaseOrder;
import static vttp.paf.day27cnyconsumer.utils.SQLConstants.SQL_INSERT_LI;
import static vttp.paf.day27cnyconsumer.utils.SQLConstants.SQL_INSERT_PO;

@Repository
public class PurchaseOrderRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Boolean insertPurchaseOrder(PurchaseOrder purchaseOrder) {
        int iUpdated = jdbcTemplate.update(SQL_INSERT_PO,purchaseOrder.getPoId(),
            purchaseOrder.getName(),purchaseOrder.getAddress(),purchaseOrder.getDeliveryDate());
        return iUpdated > 0;
    }

    public Boolean insertLineItems(PurchaseOrder purchaseOrder,String poId) {
        List<LineItem> lis = purchaseOrder.getLineItems();
        

      
        List<Object[]> params = lis.stream()
            .map(li -> {
                Object[] rec = new Object[4];
                rec[0] = li.getName();
                rec[1] = li.getQuantity();
                rec[2] = li.getUnitPrice();
                rec[3] = poId;
                return rec;
            }).toList();
        
        int[] iUpdated = jdbcTemplate.batchUpdate(SQL_INSERT_LI,params);
        return (Arrays.stream(iUpdated).allMatch(updatedRows -> updatedRows > 0));

        
    }


}
