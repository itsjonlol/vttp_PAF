package vttp.batch5.paf.day27.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch5.paf.day27.models.PurchaseOrder;
import vttp.batch5.paf.day27.repositories.PurchaseOrderRepo;
import vttp.batch5.paf.day27.util.OrderJsonFormatter;

@Service
public class PurchaseOrderService {
  @Autowired
  PurchaseOrderRepo poRepo;

  @Autowired
  @Qualifier("myredis")
  RedisTemplate<String, String> redisTemplate;

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

  public void testPubSub(PurchaseOrder po) {
    redisTemplate.convertAndSend("testpubsub",OrderJsonFormatter.pojoToJson(po));

  }



  

}
