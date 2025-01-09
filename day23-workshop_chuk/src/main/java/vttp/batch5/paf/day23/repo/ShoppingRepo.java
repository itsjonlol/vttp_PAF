package vttp.batch5.paf.day23.repo;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Boolean addPurchaseOrderAndLineItems(PurchaseOrder purchaseOrder) {

        List<LineItem> lineItems = purchaseOrder.getLineItems();
        KeyHolder keyHolder = new GeneratedKeyHolder();

    // Insert into purchaseorders and retrieve the generated purchase_id
    int added = template.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(SQL_ADD_PURCHASEORDER, new String[] { "purchase_id" });
        ps.setString(1, purchaseOrder.getUsername());
        ps.setString(2, purchaseOrder.getAddress());
        ps.setDate(3, new java.sql.Date(purchaseOrder.getDeliveryDate().getTime()));
        return ps;
    }, keyHolder);

    // Get the generated purchase_id
    Number generatedId = keyHolder.getKey();
    if (generatedId == null) {
        throw new IllegalStateException("Failed to retrieve purchase_id");
    }
    int purchaseId = generatedId.intValue();

        // int added = template.update(SQL_ADD_PURCHASEORDER,purchaseOrder.getUsername(),purchaseOrder.getAddress(),
        // purchaseOrder.getDeliveryDate());
        
        List<Object[]> params = lineItems.stream().map(item -> new Object[]{
            item.getName(),item.getQuantity(),item.getQuantity(),purchaseId
        } ).collect(Collectors.toList());

        template.batchUpdate(SQL_ADD_LINEITEMS,params);
        return added>0;
    }
    
    
    // public Boolean updateRSVP(RSVP rsvp,String email) {
    //     int updated = template.update(SQL_UPDATE_RSVP,rsvp.getEmail(),rsvp.getPhone(),rsvp.getConfirmDate(),rsvp.getComments(),email);
    //     return updated>0;
    // }

    // public Integer getRSVPCount() {
    //     return null;
    // }

    


}
