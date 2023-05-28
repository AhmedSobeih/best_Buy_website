package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.CustomerRep;
import com.FAM.messageApp.service.CustomerRepService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer_rep")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class CustomerRepController {
    @Autowired
    private CustomerRepService service;
    @PostMapping("save")
    public CustomerRep save(@RequestBody CustomerRep customerRep){
        return service.save(customerRep);
    }
    @GetMapping("get")
    public CustomerRep findCustomerRep(){
        return service.findCustomerRep();
    }
    @GetMapping("get_all")
    public List<CustomerRep> findAll(){
        return service.findAll();
    }
    @GetMapping("take")
    public void takeCustomerRep(){
        service.takeCustomerRep();
    }
    @GetMapping("remove")
    public void removeCustomerRep(){
        service.removeRep();
    }
    @GetMapping("end_chat/{id}")
    public void endChat(@PathVariable int id){
        service.customerRepEndChat(service.findById(id));
    }

    @GetMapping("remove_all")
    public void removeAll(){
        service.removeAll();
    }

}
