package com.FAM.messageApp.dao;

import com.FAM.messageApp.model.CustomerRep;

import java.util.List;
import java.util.Optional;

public interface CustomerRepRepository {
    CustomerRep save(CustomerRep customerRep);
    List<CustomerRep> findAll();
    CustomerRep findCustomerRepById(int id);
    CustomerRep deleteById(int id);
    Optional<CustomerRep> findSuitableCustomerRep();
}
