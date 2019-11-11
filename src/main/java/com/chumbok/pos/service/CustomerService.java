package com.chumbok.pos.service;

import com.chumbok.pos.entity.Customer;

public interface CustomerService {
    Customer findById(long id);

    Customer findByName(String firstName, String lastName);

    boolean isExist(String curp);


}
