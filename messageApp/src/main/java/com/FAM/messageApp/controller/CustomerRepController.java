package com.FAM.messageApp.controller;

import com.FAM.messageApp.model.CustomerRep;
import com.FAM.messageApp.service.CustomerRepService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer-rep")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", allowedHeaders = "*")
public class CustomerRepController {
    @Autowired
    private CustomerRepService customerRepService;
    @PostMapping("save")
    public CustomerRep save(@RequestBody CustomerRep customerRep){
        return customerRepService.save(customerRep);
    }
    @GetMapping("get")
    public CustomerRep findCustomerRep(){
        return customerRepService.findCustomerRep();
    }
    @GetMapping("get-all")
    public List<CustomerRep> findAll(){
        return customerRepService.findAll();
    }
    @GetMapping("take")
    public void takeCustomerRep(){
        customerRepService.takeCustomerRep();
    }
    @GetMapping("remove")
    public void removeCustomerRep(){
        customerRepService.removeRep();
    }
    @GetMapping("end-chat/{id}")
    public void endChat(@PathVariable int id){
        customerRepService.customerRepEndChat(customerRepService.findById(id));
    }

    @GetMapping("remove-all")
    public void removeAll(){
        customerRepService.removeAll();
    }

}
