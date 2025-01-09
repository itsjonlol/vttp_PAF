package vttp.batch5.paf.day23.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.paf.day23.model.PurchaseOrder;
import vttp.batch5.paf.day23.repo.ShoppingRepo;

@Service
public class ShoppingService {
    
    @Autowired
    ShoppingRepo shoppingRepo;

    public void addShopping(PurchaseOrder purchaseOrder){
        
        shoppingRepo.addPurchaseOrderAndLineItems(purchaseOrder);
    }
}
