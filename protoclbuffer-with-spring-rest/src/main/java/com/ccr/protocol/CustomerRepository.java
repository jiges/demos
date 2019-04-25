package com.ccr.protocol;

import java.util.Map;

/**
 * @author ccr12312@163.com at 2019-4-24
 */
public class CustomerRepository {

    private Map<Integer, CustomerProtos.Customer> customers;

    public CustomerRepository(Map<Integer, CustomerProtos.Customer> customers) {
        this.customers = customers;
    }

    public CustomerProtos.Customer getCustomer(Integer id) {
        return customers.get(id);
    }

}
