package com.chumbok.pos.service;

import com.chumbok.pos.dto.ReturnVoucherDTO;
import com.chumbok.pos.entity.Customer;
import com.chumbok.pos.entity.ReturnVoucher;

import java.util.List;

public interface ReturnVoucherService {
    List<ReturnVoucher> findAll();

    List<ReturnVoucher> findByCustomer(Customer customer);
}
