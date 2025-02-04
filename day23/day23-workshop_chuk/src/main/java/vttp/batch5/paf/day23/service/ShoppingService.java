package vttp.batch5.paf.day23.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch5.paf.day23.model.PurchaseOrder;
import vttp.batch5.paf.day23.repo.ShoppingRepo;

@Service
public class ShoppingService {
    
    @Autowired
    ShoppingRepo shoppingRepo;

    @Transactional
    public Boolean addShopping(PurchaseOrder purchaseOrder){
        Boolean updated;
        Integer purchaseId = shoppingRepo.addPurchaseOrder(purchaseOrder);
        shoppingRepo.insertLineItems(purchaseOrder, purchaseId);
        updated = true;
        return updated;
    }
}
