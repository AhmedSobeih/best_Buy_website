package com.FAM.messageApp.service;

import com.FAM.messageApp.model.CustomerRep;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CustomerRepService {
    private static final String HASH_KEY = "customer_rep_queue";

    @Autowired
    private RedisTemplate redisTemplate;



    public CustomerRep save(CustomerRep customerRep){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(HASH_KEY, customerRep, customerRep.getNoOfChats());
        log.info("Representative is added to the cache");
        return customerRep;
    }
    public CustomerRep findCustomerRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Set<CustomerRep> highestPriorityElements = zSetOps.range(HASH_KEY, 0, 0);
        if (!highestPriorityElements.isEmpty()) {
            CustomerRep best = highestPriorityElements.iterator().next();
            log.info("Representative should take this" + best.getUsername());
            return best;
        } else {
            log.info("No Available Representatives");
            return null;
        }
    }
    public List<CustomerRep> findAll(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null) {
            Set<CustomerRep> list = zSetOps.range(HASH_KEY, 0, size);
            List<CustomerRep> theList = new ArrayList<>(list);
            return theList;
        }
        return null;
    }
    public CustomerRep removeRep(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        CustomerRep removed = findCustomerRep();
        zSetOps.remove(HASH_KEY, removed);
        log.info("Representative " + removed.getUsername() + " is removed.");
        return removed;
    }
    public void takeCustomerRep(){
        CustomerRep rep = removeRep();
        rep.setNoOfChats(rep.getNoOfChats()+1);
        save(rep);
        log.info("Representative " + rep.getUsername() + " is handling that inquiry");
    }

    public void customerRepEndChat(CustomerRep customerRep){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        zSetOps.remove(HASH_KEY, customerRep);
        customerRep.setNoOfChats(customerRep.getNoOfChats()-1);
        save(customerRep);
        log.info("Representative " + customerRep.getUsername() + " ended his chat");
    }
    public CustomerRep findById(int id){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null){
            Set<CustomerRep> elements = zSetOps.range(HASH_KEY, 0, size);
            ArrayList<CustomerRep> list = new ArrayList<>(elements);
            for(CustomerRep rep: list){
                if(id == rep.getId()){
                    return rep;
                }
            }
        }
        return null;
    }
    public void removeAll(){
        ZSetOperations<String, CustomerRep> zSetOps = redisTemplate.opsForZSet();
        Long size = zSetOps.size(HASH_KEY);
        if(size!=null)
            zSetOps.removeRange(HASH_KEY, 0, size);
    }
}
