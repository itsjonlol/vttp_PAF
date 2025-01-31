package vttp.batch5.paf.day27.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.day27.models.PurchaseOrder;
import vttp.batch5.paf.day27.util.OrderJsonFormatter;

@Repository
public class RedisRepo {
    
    @Autowired
    @Qualifier("myredis")
    RedisTemplate<String, String> redisTemplate;

    public void pushToRepo(PurchaseOrder po) {
        redisTemplate.opsForList().rightPush("events",OrderJsonFormatter.pojoToJson(po));
    }
    

}
