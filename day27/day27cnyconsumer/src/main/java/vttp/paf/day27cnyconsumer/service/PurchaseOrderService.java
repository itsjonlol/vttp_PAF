package vttp.paf.day27cnyconsumer.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.paf.day27cnyconsumer.models.PurchaseOrder;
import vttp.paf.day27cnyconsumer.repo.PurchaseOrderRepo;

@Service
public class PurchaseOrderService {
    @Autowired
    PurchaseOrderRepo poRepo;

    public String createPurchaseOrder(PurchaseOrder po) {
        String poId = UUID.randomUUID().toString().substring(0, 8);

        return poId;
    }

    @Transactional
    public Boolean insertPo(PurchaseOrder po) {
        Boolean bCreated = false;

        poRepo.insertPurchaseOrder(po);
        poRepo.insertLineItems(po, po.getPoId());

        bCreated = true;
        return bCreated;
    }
}
