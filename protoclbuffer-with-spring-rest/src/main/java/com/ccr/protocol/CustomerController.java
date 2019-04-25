package com.ccr.protocol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccr12312@163.com at 2019-4-24
 */
@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping("/customers/{id}")
    public CustomerProtos.Customer customer(@PathVariable Integer id){
        return repository.getCustomer(id);
    }
}
